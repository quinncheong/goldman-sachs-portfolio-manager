package com.is442g1t4.gpa.portfolio.portfolioanalyzer;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is442g1t4.gpa.stock.StockService;
import com.is442g1t4.gpa.stock.stockPrice.StockPriceService;
import com.is442g1t4.gpa.stock.stockPrice.StockPrice;
import com.is442g1t4.gpa.stock.model.Stock;
import com.is442g1t4.gpa.user.User;
import com.is442g1t4.gpa.user.UserRepository;
import com.is442g1t4.gpa.portfolio.Portfolio;
import com.is442g1t4.gpa.portfolio.PortfolioRepository;
import com.is442g1t4.gpa.portfolio.PortfolioService;
import com.is442g1t4.gpa.portfolio.portfolioCalculator.PortfolioCalculator;
import com.is442g1t4.gpa.portfolio.portfolioCalculator.PortfolioCalculatorUtility;
import com.is442g1t4.gpa.portfolio.allocatedStock.AllocatedStock;
import com.is442g1t4.gpa.portfolio.allocatedStock.AllocatedStockService;
import com.is442g1t4.gpa.portfolio.allocatedStock.AllocatedStockRepository;

import java.util.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.Instant;

@Service
public class PortfolioAnalyzerService {

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private StockService stockService;

    @Autowired
    private StockPriceService stockPriceService;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public Map<String, Double> getPortfolioAnalysis(ObjectId id) {
        Map<String, Double> result = new HashMap<>();
        
        List<AllocatedStock> allocatedStocks = portfolioService.getAllAllocatedStocksInPortfolio(id);
        if (allocatedStocks.size() == 0) {
            result.put("dpnl", 0.0);
            result.put("pnl", 0.0);
            result.put("dpnla", 0.0);
            result.put("pnla", 0.0);
            result.put("value", 0.0);
            return result;
        }

        double cost = 0;
        double value = 0;
        double previousValue = 0;
        Double pnl;
        Double dpnl;
        Double dpnla;
        Double pnla;

        for (AllocatedStock allocatedStock : allocatedStocks) {
            String stockTicker = allocatedStock.getStockTicker();
            Stock stock = stockService.getStockByTicker(stockTicker).get();
            int quantity = allocatedStock.getStockQuantity();
            Date buyDate = allocatedStock.getStockBuyDate();
            Instant instant = buyDate.toInstant();

            cost = cost + (allocatedStock.getStockBuyPrice() * quantity);
            // value  = value + (90 * quantity);
            double priceToday = stock.getPriceToday();
            value  = value + (priceToday * quantity);

            if(instant.atZone(ZoneId.systemDefault()).toLocalDate().equals(LocalDate.now()) ){
                // previousValue = previousValue + (90 * quantity);
                previousValue = previousValue + (priceToday * quantity);
            } else {
                // previousValue = previousValue + (100 * quantity);
                previousValue = previousValue + (stock.getPriceYesterday() * quantity);
            }
        }

        dpnl = PortfolioCalculatorUtility.round((value / previousValue - 1) * 100);
        pnl = PortfolioCalculatorUtility.round((value / cost - 1) * 100);
        dpnla = PortfolioCalculatorUtility.round((value - previousValue));
        pnla = PortfolioCalculatorUtility.round(value - cost);

        result.put("dpnl", dpnl);
        result.put("pnl", pnl);
        result.put("dpnla", dpnla);
        result.put("pnla", pnla);
        result.put("value",PortfolioCalculatorUtility.round(value));

        return result;
    }

}

//     public Map<Date, Map<String, Double>> getPortfolioAnalysisByTime(ObjectId id, String startString, String endString, String type) {
//         Date startDate;  
//         Date endDate;
//         try {

//             if (type == "month") { 
//                 SimpleDateFormat formatter = new SimpleDateFormat("MMyyyy");
//                 startDate = formatter.parse(startString);
//                 endDate = formatter.parse(endString);
//             } else {
//                 SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
//                 startDate = formatter.parse(startString);
//                 endDate = formatter.parse(endString);
//             }
//         } catch (java.text.ParseException e) {
//             e.printStackTrace();
//             return null;
//         }

//         Calendar start = Calendar.getInstance();
//         start.setTime(startDate);
//         Calendar end = Calendar.getInstance();
//         end.setTime(endDate);
        

//         Map<Date, Map<String, Double>> result = new HashMap<>();
//         Map<String, Double> temp = new HashMap<>();
//         List<AllocatedStock> allocatedStocks = portfolioService.getAllAllocatedStocksInPortfolio(id);
//         if (allocatedStocks.size() == 0) {
//             temp.put("dpnl", 0.0);
//             temp.put("pnl", 0.0);
//             temp.put("dpnla", 0.0);
//             temp.put("pnla", 0.0);
//             temp.put("value", 0.0);
//             result.put(startDate, temp);
//             return result;
//         }

//         double cost = 0;
//         double value = 0;
//         double previousValue = 0;
//         Double pnl;
//         Double dpnl;
//         Double dpnla;
//         Double pnla;

//         for (AllocatedStock allocatedStock : allocatedStocks) {
//             String stockTicker = allocatedStock.getStockTicker();
//             // Stock stock = stockService.getStockByTicker(stockTicker).get();
//             for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
//                 StockPrice stockPrice = stockPriceService.getStockPriceBySymbolAndDate(stockTicker,);
//                 int quantity = allocatedStock.getStockQuantity();
//                 Date buyDate = allocatedStock.getStockBuyDate();
//                 Instant instant = buyDate.toInstant();

//                 cost = cost + (allocatedStock.getStockBuyPrice() * quantity);
//                 double priceToday = stock.getPriceToday();
//                 value  = value + (priceToday * quantity);

//                 if(instant.atZone(ZoneId.systemDefault()).toLocalDate().equals(LocalDate.now()) ){
//                     previousValue = previousValue + (priceToday * quantity);
//                 } else {
//                     previousValue = previousValue + (stock.getPriceYesterday() * quantity);
//                 }
//             }
//             StockPrice stockPrice = stockPriceService.getStockPriceByTickerBySymbolAndDate(stockTicker,date);
//             int quantity = allocatedStock.getStockQuantity();
//             Date buyDate = allocatedStock.getStockBuyDate();
//             Instant instant = buyDate.toInstant();

//             cost = cost + (allocatedStock.getStockBuyPrice() * quantity);
//             double priceToday = stock.getPriceToday();
//             value  = value + (priceToday * quantity);

//             if(instant.atZone(ZoneId.systemDefault()).toLocalDate().equals(LocalDate.now()) ){
//                 previousValue = previousValue + (priceToday * quantity);
//             } else {
//                 previousValue = previousValue + (stock.getPriceYesterday() * quantity);
//             }
//         }

//         dpnl = PortfolioCalculatorUtility.round((value / previousValue - 1) * 100);
//         pnl = PortfolioCalculatorUtility.round((value / cost - 1) * 100);
//         dpnla = PortfolioCalculatorUtility.round((value - previousValue));
//         pnla = PortfolioCalculatorUtility.round(value - cost);

//         temp.put("dpnl", dpnl);
//         temp.put("pnl", pnl);
//         temp.put("dpnla", dpnla);
//         temp.put("pnla", pnla);
//         temp.put("value",PortfolioCalculatorUtility.round(value));
//     return result;
//     }

    
// }
