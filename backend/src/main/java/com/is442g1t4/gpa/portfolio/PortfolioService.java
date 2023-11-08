package com.is442g1t4.gpa.portfolio;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is442g1t4.gpa.user.User;
import com.is442g1t4.gpa.user.UserRepository;
import com.is442g1t4.gpa.portfolio.PortfolioService;
import com.is442g1t4.gpa.portfolio.allocatedStock.AllocatedStock;
import com.is442g1t4.gpa.portfolio.allocatedStock.AllocatedStockService;
import com.is442g1t4.gpa.portfolio.portfolioCalculator.PortfolioCalculator;
import com.is442g1t4.gpa.portfolio.portfolioCalculator.PortfolioCalculatorUtility;
import com.is442g1t4.gpa.stock.stockPrice.StockPrice;
import com.is442g1t4.gpa.stock.stockPrice.StockPriceService;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PortfolioService {
    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AllocatedStockService allocatedStockService;

    @Autowired
    private StockPriceService stockPriceService;

    public List<Portfolio> getAllPortfolios() {
        return portfolioRepository.findAll();
    }

    public Optional<Portfolio> getPortfolioByPortfolioId(ObjectId id) {
        return portfolioRepository.findById(id);
    }

    public List<Portfolio> getPortfoliosByUserId(ObjectId userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return portfolioRepository.findByUserId(user.get().getId());
        } else {

            return Collections.<Portfolio>emptyList();
        }
    }

    public Portfolio createPortfolio(Portfolio portfolio) {
        if (portfolio.getId() != null) {
            return null;
        }
        portfolio.setId(new ObjectId());
        return portfolioRepository.save(portfolio);
    }

    public Portfolio updatePortfolio(Portfolio portfolio) {
        return portfolioRepository.save(portfolio);
    }

    public String deletePortfolioByPortfolioId(ObjectId id) {
        portfolioRepository.deleteById(id);
        return "Portfolio Deleted";
    }

    public Portfolio addStockToPortfolio(String symbol, int quantity, ObjectId portfolioId) {
        Optional<Portfolio> portfolio = portfolioRepository.findById(portfolioId);
        if (portfolio.isPresent()) {
            portfolio.get().getAllocatedStocks()
                    .add(allocatedStockService.addAllocatedStock(symbol, quantity));
        }
        return portfolioRepository.save(portfolio.get());
    }

    public Portfolio delStocksFromPortfolio(ObjectId portfolioId) {
        Optional<Portfolio> portfolio = portfolioRepository.findById(portfolioId);
        if (portfolio.isPresent()) {
            portfolio.get().setAllocatedStocks(Collections.<AllocatedStock>emptyList());
            return portfolioRepository.save(portfolio.get());
        }
        return null;

    }

    public List<AllocatedStock> getAllAllocatedStocksInPortfolio(ObjectId id) {

        Portfolio retrievedPortfolio = portfolioRepository.findPortfolioById(id);
        List<AllocatedStock> allocatedStocks = retrievedPortfolio.getAllocatedStocks();
        return allocatedStocks;
    }

    public Portfolio addAllocatedStock(AllocatedStock allocatedStock, ObjectId portfolioId,
            ObjectId userId) {

        AllocatedStock savedAllocatedStock =
                allocatedStockService.addAllocatedStock(allocatedStock);

        double allocatedStockValue = 0.0;
        double userCashBalance = 0.0;
        Optional<Portfolio> portfolio = portfolioRepository.findById(portfolioId);
        if (portfolio.isPresent()) {

            Optional<User> user = userRepository.findById(userId);
            if (user.isPresent()) {
                allocatedStockValue = savedAllocatedStock.getStockQuantity()
                        * savedAllocatedStock.getStockBuyPrice();

                userCashBalance = user.get().getCashBalance();
                if (userCashBalance >= allocatedStockValue) {

                    user.get().setCashBalance(userCashBalance - allocatedStockValue);
                    userRepository.save(user.get());
                    userCashBalance = user.get().getCashBalance();

                    portfolio.get().getAllocatedStocks().add(savedAllocatedStock);
                } else {

                    return null;
                }
            }
        }
        return portfolioRepository.save(portfolio.get());
    }

    public Portfolio deleteAllocatedStockFromPortfolio(ObjectId portfolioId, String stockTicker) {
        Optional<Portfolio> portfolio = portfolioRepository.findById(portfolioId);
        List<AllocatedStock> newAllocatedStocks = new ArrayList<>();

        if (portfolio.isPresent()) {

            List<AllocatedStock> allocatedStocks = portfolio.get().getAllocatedStocks();

            for (AllocatedStock allocatedStock : allocatedStocks) {
                if (!allocatedStock.getStockTicker().equals(stockTicker)) {
                    newAllocatedStocks.add(allocatedStock);
                } else {
                    double allocatedStockValue =
                            allocatedStock.getStockQuantity() * allocatedStock.getStockBuyPrice();
                    double portfolioInitialValue = portfolio.get().getInitialValue();
                    portfolio.get().setInitialValue(portfolioInitialValue + allocatedStockValue);
                    portfolioRepository.save(portfolio.get());
                }
            }
        }
        portfolio.get().setAllocatedStocks(newAllocatedStocks);
        Portfolio retrievedPortfolio = portfolioRepository.save(portfolio.get());
        return retrievedPortfolio;
    }

    public Double getPortfolioStockExAndVar(Map<String, PortfolioCalculator> calculated) {
        Double ror = 0.0;
        Map<String, Map<String, Double>> result = new HashMap<>();
        for (String stockTicker : calculated.keySet()) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            cal.add(Calendar.DATE, -15);
            PortfolioCalculator cPortfolioCalculator = calculated.get(stockTicker);
            Map<String, Double> temp = new HashMap<>();
            ArrayList<Double> stockPrices = new ArrayList<>();
            ArrayList<Double> percentages = new ArrayList<>();
            System.out.println(stockTicker);
            System.out.println(cal.getTime());
            StockPrice stockPrice1 =
                    stockPriceService.getStockPriceBySymbolAndDate(stockTicker, cal.getTime());
            while (stockPrice1 == null) {
                cal.add(Calendar.DATE, -3);
                stockPrice1 =
                        stockPriceService.getStockPriceBySymbolAndDate(stockTicker, cal.getTime());
            }
            System.out.println(stockPrice1);
            stockPrices.add(stockPrice1.getClose());
            cal.add(Calendar.YEAR, -6);
            stockPrice1 =
                    stockPriceService.getStockPriceBySymbolAndDate(stockTicker, cal.getTime());
            while (stockPrice1 == null) {
                cal.add(Calendar.DATE, -3);
                stockPrice1 =
                        stockPriceService.getStockPriceBySymbolAndDate(stockTicker, cal.getTime());
            }
            stockPrices.add(stockPrice1.getClose());
            Double percGrowth =
                    (stockPrices.get(0) / stockPrices.get(stockPrices.size() - 1) - 1) * 100;

            Double ex;
            if (percGrowth < 0) {
                ex = PortfolioCalculatorUtility.round(Math.pow(-percGrowth, 1.0 / 5.0)) * -1.0;
            } else {
                ex = PortfolioCalculatorUtility.round(Math.pow(percGrowth, 1.0 / 5.0));
            }
            System.out.println(stockPrices.get(0));
            System.out.println(stockPrices.get(stockPrices.size() - 1));
            System.out.println(percGrowth);
            Double sum = 0.0;
            for (Double perc : percentages) {
                sum += Math.pow(perc - ex, 2);
            }
            Double var = PortfolioCalculatorUtility.round(Math.pow(sum / 5.0, 1.0 / 2.0));
            temp.put("ex", ex);
            temp.put("var", var);
            ror += PortfolioCalculatorUtility
                    .round(ex * cPortfolioCalculator.getPositionsRatio() / 100);
            result.put(stockTicker, temp);
        }
        Map<String, Double> portfolio = new HashMap<>();
        ror = PortfolioCalculatorUtility.round(ror);
        portfolio.put("ex", ror);
        result.put("portfolio", portfolio);

        return ror;
    }

    public Map<String, Double> getMonthlyPortfolioValueByDateRange(
            Map<String, PortfolioCalculator> calculated, Date startDate, Date endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Map<String, Double> result = new TreeMap<>();
        Calendar curr = Calendar.getInstance();
        curr.setTime(endDate);
        while ((curr.getTime().getTime() - startDate.getTime()) > 0) {
            Double val = 0.0;
            StockPrice test =
                    stockPriceService.getStockPriceBySymbolAndDate("AAPL", curr.getTime());
            while (test == null) {
                curr.add(Calendar.DATE, -2);
                test = stockPriceService.getStockPriceBySymbolAndDate("AAPL", curr.getTime());
            }
            for (String stockTicker : calculated.keySet()) {
                StockPrice stockPrice =
                        stockPriceService.getStockPriceBySymbolAndDate(stockTicker, curr.getTime());
                while (stockPrice == null) {
                    stockPrice = stockPriceService.getStockPriceBySymbolAndDate(stockTicker,
                            curr.getTime());
                }
                val += stockPrice.getClose() * (calculated.get(stockTicker).getPosition() * 1.0);
            }
            result.put(sdf.format(curr.getTime()), PortfolioCalculatorUtility.round(val));
            curr.add(Calendar.MONTH, -1);
        }
        return result;
    }
}
