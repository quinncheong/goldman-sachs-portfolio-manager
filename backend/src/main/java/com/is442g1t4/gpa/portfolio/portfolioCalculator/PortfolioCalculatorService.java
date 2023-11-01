package com.is442g1t4.gpa.portfolio.portfolioCalculator;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is442g1t4.gpa.stock.StockService;
import com.is442g1t4.gpa.user.User;
import com.is442g1t4.gpa.user.UserRepository;
import com.is442g1t4.gpa.stock.model.Stock;
import com.is442g1t4.gpa.portfolio.Portfolio;
import com.is442g1t4.gpa.portfolio.PortfolioRepository;
import com.is442g1t4.gpa.portfolio.PortfolioService;
import com.is442g1t4.gpa.portfolio.allocatedStock.AllocatedStock;
import com.is442g1t4.gpa.portfolio.allocatedStock.AllocatedStockService;
import com.is442g1t4.gpa.portfolio.allocatedStock.AllocatedStockRepository;

import java.util.*;
import java.time.Instant;

@Service
public class PortfolioCalculatorService {
    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private StockService stockService;

    

    public Map<String, PortfolioCalculator> getCalculatedStockInPortfolio(ObjectId id) {
        Map<String, PortfolioCalculator> calculatedStock = new HashMap<>();
        double totalValue = 0;

        List<AllocatedStock> allocatedStocks = portfolioService.getAllAllocatedStocksInPortfolio(id);


        for (AllocatedStock allocatedStock : allocatedStocks){
            String stockTicker = allocatedStock.getStockTicker();
            Instant instant = allocatedStock.getStockBuyDate().toInstant();
            int quantity = allocatedStock.getStockQuantity();

            if (!calculatedStock.containsKey(stockTicker)){
                PortfolioCalculator portfolioCalculator = new PortfolioCalculator(stockTicker, quantity, quantity * allocatedStock.getStockBuyPrice(), 0.0, 0.0, 0.0, 0.0);
                PortfolioCalculatorUtility.setProfitsAndLosses(instant, portfolioCalculator, quantity, 1);
                calculatedStock.put(stockTicker, portfolioCalculator);
            }else{
                PortfolioCalculator portfolioCalculator = calculatedStock.get(stockTicker);
                portfolioCalculator.setStockQuantity(portfolioCalculator.getStockQuantity() + quantity);
                portfolioCalculator.setAvgStockBuyPrice(portfolioCalculator.getAvgStockBuyPrice() + quantity * allocatedStock.getStockBuyPrice());
                PortfolioCalculatorUtility.setProfitsAndLosses(instant, portfolioCalculator, quantity, 0);
            }
        }
        for (String stockTicker : calculatedStock.keySet()){
            PortfolioCalculator portfolioCalculator = calculatedStock.get(stockTicker);
            double priceToday = stockService.getPriceTodayByTicker(stockTicker);
            // Stock stock = stockService.getStockByTicker(stockTicker).get();
            // double value = portfolioCalculator.getStockQuantity() *90;
            double value = portfolioCalculator.getStockQuantity() * priceToday;
            double quantity = portfolioCalculator.getStockQuantity();
            // double previousValue = portfolioCalculator.getPnl() * 90 + portfolioCalculator.getDpnl() * 100;
            double previousValue = portfolioCalculator.getPnl() * priceToday + portfolioCalculator.getDpnl() * stockService.getPriceYesterdayByTicker(stockTicker);
            portfolioCalculator.setAvgStockBuyPrice(portfolioCalculator.getAvgStockBuyPrice() / quantity);
            totalValue = totalValue + value;
            portfolioCalculator.setDpnl(value/previousValue - 1);
            portfolioCalculator.setCurrentValue(value);
            
            portfolioCalculator.setPnl(value/ (portfolioCalculator.getAvgStockBuyPrice() * quantity) - 1);
        }
        for (String stockTicker : calculatedStock.keySet()){

            PortfolioCalculator portfolioCalculator = calculatedStock.get(stockTicker);
            portfolioCalculator.setWeight(portfolioCalculator.getCurrentValue() / totalValue);
        }
        return calculatedStock;
    }

}
