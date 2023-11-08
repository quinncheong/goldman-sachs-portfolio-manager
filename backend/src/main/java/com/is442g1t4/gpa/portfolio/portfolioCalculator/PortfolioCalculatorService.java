package com.is442g1t4.gpa.portfolio.portfolioCalculator;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is442g1t4.gpa.stock.StockService;
import com.is442g1t4.gpa.stock.model.Stock;
import com.is442g1t4.gpa.stock.stockPrice.StockPriceService;
import com.is442g1t4.gpa.portfolio.PortfolioService;
import com.is442g1t4.gpa.portfolio.allocatedStock.AllocatedStock;
import com.is442g1t4.gpa.stock.stockPrice.StockPrice;

import java.util.*;
import java.text.SimpleDateFormat;
import java.time.Instant;

@Service
public class PortfolioCalculatorService {
    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private StockService stockService;

    @Autowired
    private StockPriceService stockPriceService;
    

    public Map<String, PortfolioCalculator> getCalculatedStockInPortfolio(ObjectId id) {
        Map<String, PortfolioCalculator> calculatedStock = new HashMap<>();
        double totalValue = 0;

        List<AllocatedStock> allocatedStocks = portfolioService.getAllAllocatedStocksInPortfolio(id);

        for (AllocatedStock allocatedStock : allocatedStocks){
            String stockTicker = allocatedStock.getStockTicker();
            Instant instant = allocatedStock.getStockBuyDate().toInstant();
            int quantity = allocatedStock.getStockQuantity();

            if (!calculatedStock.containsKey(stockTicker)){
                PortfolioCalculator portfolioCalculator = new PortfolioCalculator(stockTicker, quantity, quantity * allocatedStock.getStockBuyPrice());
                PortfolioCalculatorUtility.setProfitsAndLosses(instant, portfolioCalculator, quantity, 1);
                calculatedStock.put(stockTicker, portfolioCalculator);
            }else{
                PortfolioCalculator portfolioCalculator = calculatedStock.get(stockTicker);
                portfolioCalculator.setPosition(portfolioCalculator.getPosition() + quantity);
                portfolioCalculator.setCost(portfolioCalculator.getCost() + quantity * allocatedStock.getStockBuyPrice());
                PortfolioCalculatorUtility.setProfitsAndLosses(instant, portfolioCalculator, quantity, 0);
            }
        }
        for (String stockTicker : calculatedStock.keySet()){
            Stock stock = stockService.getStockByTicker(stockTicker).get();
            PortfolioCalculator portfolioCalculator = calculatedStock.get(stockTicker);
            double priceToday = stock.getPriceToday();
            double value = portfolioCalculator.getPosition() * priceToday;
            double quantity = portfolioCalculator.getPosition();
            double previousValue = portfolioCalculator.getPnlp() * priceToday + portfolioCalculator.getDpnlp() * stock.getPriceYesterday();
            portfolioCalculator.setCost(portfolioCalculator.getCost() / quantity);
            totalValue = totalValue + value;
            portfolioCalculator.setLast(priceToday);
            portfolioCalculator.setDpnla(PortfolioCalculatorUtility.round(value - previousValue));
            portfolioCalculator.setDpnlp(PortfolioCalculatorUtility.round((value/previousValue - 1) * 100));
            portfolioCalculator.setMarket(PortfolioCalculatorUtility.round(value));
            portfolioCalculator.setPnlp(PortfolioCalculatorUtility.round((value/ (portfolioCalculator.getCost() * quantity) - 1) * 100));
            portfolioCalculator.setPnla(PortfolioCalculatorUtility.round(value - portfolioCalculator.getCost() * quantity));
            portfolioCalculator.setCountry(stock.getCountry());
            portfolioCalculator.setStockName(stock.getName());
        }
        // positions ratio and sector analysis
        for (String stockTicker : calculatedStock.keySet()){
            PortfolioCalculator portfolioCalculator = calculatedStock.get(stockTicker);
            portfolioCalculator.setPositionsRatio((portfolioCalculator.getMarket() / totalValue) * 100);
        }
        return calculatedStock;
    }

    public Map<String, Map<String,Double>> getStockData(Map<String,PortfolioCalculator> calculated) {
        Map<String, Map<String,Double>> data = new HashMap<>();
        Map<String,Double> countryData = new HashMap<>();
        Map<String,Double> sectorData = new HashMap<>();

        for (String stockTicker : calculated.keySet()) {
            Stock stock = stockService.getStockByTicker(stockTicker).get();
            String sector = stock.getSector();
            String country = stock.getCountry();

            PortfolioCalculator portfolioCalculator = calculated.get(stockTicker);
            double positionsRatio = portfolioCalculator.getPositionsRatio();

            if (!(countryData.containsKey(country))) {
                countryData.put(country, positionsRatio);
            }
            else {
                double previous = countryData.get(country);
                countryData.put(country, previous + positionsRatio);
            }
            if (!(sectorData.containsKey(sector))) {
                sectorData.put(sector, positionsRatio);
            }
            else {
                double previous = sectorData.get(sector);
                sectorData.put(sector, previous + positionsRatio);
            }

        }

        data.put("sector",sectorData);
        data.put("country",countryData);


        return data;
    }   

    public Map<String, Double> getAdjustedMonthlyPortfolioValueByDateRange(ObjectId id, Date startDate, Date endDate){
        List<AllocatedStock> allocatedStocks = portfolioService.getAllAllocatedStocksInPortfolio(id);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Map<String, Double> result = new TreeMap<>();
        Calendar curr = Calendar.getInstance();
        curr.setTime(endDate);
        while((curr.getTime().getTime() - startDate.getTime()) > 0){
            Double val = 0.0;
            StockPrice test = stockPriceService.getStockPriceBySymbolAndDate("AAPL", curr.getTime());
            while (test == null){
                curr.add(Calendar.DATE, -2);
                test = stockPriceService.getStockPriceBySymbolAndDate("AAPL", curr.getTime());
            }
            List<AllocatedStock> temp = PortfolioCalculatorUtility.getAllocatedStocksByDate(allocatedStocks, curr.getTime());
            Map<String, PortfolioCalculator> calculated = PortfolioCalculatorUtility.getCalculatedStocks(temp);
            for (String stockTicker: calculated.keySet()){
                StockPrice stockPrice = stockPriceService.getStockPriceBySymbolAndDate(stockTicker, curr.getTime());
                while (stockPrice == null){
                    stockPrice = stockPriceService.getStockPriceBySymbolAndDate(stockTicker, curr.getTime());
                }
                val += stockPrice.getClose() * (calculated.get(stockTicker).getPosition() * 1.0);
            }
            result.put(sdf.format(curr.getTime()), PortfolioCalculatorUtility.round(val));
            curr.add(Calendar.MONTH, -1);
        }
        return result;
    }
}
