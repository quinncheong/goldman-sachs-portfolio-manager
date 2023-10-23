package com.is442g1t4.gpa.portfolio.portfolioCalculator;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is442g1t4.gpa.portfolio.repository.PortfolioRepository;
import com.is442g1t4.gpa.portfolio.service.PortfolioService;
import com.is442g1t4.gpa.stock.StockService;
import com.is442g1t4.gpa.user.User;
import com.is442g1t4.gpa.user.UserRepository;
import com.is442g1t4.gpa.portfolio.model.Portfolio;
import com.is442g1t4.gpa.stock.model.Stock;
import com.is442g1t4.gpa.portfolio.allocatedStock.AllocatedStock;
import com.is442g1t4.gpa.portfolio.allocatedStock.AllocatedStockService;
import com.is442g1t4.gpa.portfolio.allocatedStock.AllocatedStockRepository;

import java.util.*;

@Service
public class PortfolioCalculatorService {
    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private StockService stockService;

        public Map<String, PortfolioCalculator> getCalculatedStockInPortfolio(ObjectId id){
            Map<String, PortfolioCalculator> calculatedStock = new HashMap<>();
            double totalValue = 0;

            List<AllocatedStock> allocatedStocks = portfolioService.getAllAllocatedStocksInPortfolio(id);

            for (AllocatedStock allocatedStock : allocatedStocks){
                String stockTicker = allocatedStock.getStockTicker();

                if (!calculatedStock.containsKey(stockTicker)){
                    PortfolioCalculator portfolioCalculator = new PortfolioCalculator(stockTicker, allocatedStock.getStockQuantity(), allocatedStock.getStockBuyPrice(), 0.0, 0.0);
                    calculatedStock.put(stockTicker, portfolioCalculator);
                }else{
                    PortfolioCalculator portfolioCalculator = calculatedStock.get(stockTicker);
                    int currStockQuantity = portfolioCalculator.getStockQuantity();
                    int newStockQuantity = allocatedStock.getStockQuantity();
                    double currAvgStockBuyPrice = portfolioCalculator.getAvgStockBuyPrice();
                    double newAvgStockBuyPrice = allocatedStock.getStockBuyPrice();
                    int totalStockQuantity = currStockQuantity + newStockQuantity;
                    double avgStockBuyPrice = ((currAvgStockBuyPrice * currStockQuantity) + (newAvgStockBuyPrice * newStockQuantity)) / totalStockQuantity;
                    
                    portfolioCalculator.setStockQuantity(totalStockQuantity);
                    portfolioCalculator.setAvgStockBuyPrice(avgStockBuyPrice);
                    calculatedStock.put(stockTicker, portfolioCalculator);
                }
            }
            for (String stockTicker : calculatedStock.keySet()){
                PortfolioCalculator portfolioCalculator = calculatedStock.get(stockTicker);
                Stock stock = stockService.getStockByTicker(stockTicker).get();
                double value = portfolioCalculator.getStockQuantity() * stock.priceToday();
                totalValue = totalValue + value;
                portfolioCalculator.setCurrentValue(value);
            }
            for (String stockTicker : calculatedStock.keySet()){
                PortfolioCalculator portfolioCalculator = calculatedStock.get(stockTicker);
                portfolioCalculator.setWeight(portfolioCalculator.getCurrentValue()/totalValue);
            }
            return calculatedStock;
        }
    
}
