package com.is442g1t4.gpa.portfolio.portfolioCalculator;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import com.is442g1t4.gpa.portfolio.allocatedStock.AllocatedStock;
import com.is442g1t4.gpa.stock.model.Stock;

public class PortfolioCalculatorUtility {

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static Double round(double num){
        return Double.parseDouble(df.format(num));
    }

    public static void setProfitsAndLosses(Instant instant, PortfolioCalculator portfolioCalculator, double quantity, int state){
        if(instant.atZone(ZoneId.systemDefault()).toLocalDate().equals(LocalDate.now()) ){
            if(state == 1){
                portfolioCalculator.setPnlp(quantity);
            } else {
                portfolioCalculator.setPnlp(portfolioCalculator.getPnlp() + quantity);
            }
        } else {
            if(state == 1){
                portfolioCalculator.setDpnlp(quantity);
            } else {
                portfolioCalculator.setDpnlp(portfolioCalculator.getDpnlp() + quantity);
            }
            
        }
    }

        public static Map<String, PortfolioCalculator> getCalculatedStocks(List<AllocatedStock> allocatedStocks){
        Map<String, PortfolioCalculator> calculatedStock = new HashMap<>();

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
    
        return calculatedStock;
    }

    public static List<AllocatedStock> getAllocatedStocksByDate(List<AllocatedStock> allocatedStocks, Date beforeDate){
        List<AllocatedStock> result = new ArrayList<>();
        for (AllocatedStock a: allocatedStocks){
            if ((a.getStockBuyDate().getTime() - beforeDate.getTime()) <= 0){
                result.add(a);
            }
        }
        return result;
    }

}
