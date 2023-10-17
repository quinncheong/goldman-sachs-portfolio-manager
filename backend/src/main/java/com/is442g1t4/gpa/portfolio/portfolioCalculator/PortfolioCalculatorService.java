package com.is442g1t4.gpa.portfolio.portfolioCalculator;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is442g1t4.gpa.portfolio.repository.PortfolioRepository;
import com.is442g1t4.gpa.user.User;
import com.is442g1t4.gpa.user.UserRepository;
import com.is442g1t4.gpa.portfolio.model.Portfolio;
import com.is442g1t4.gpa.portfolio.allocatedStock.AllocatedStock;
import com.is442g1t4.gpa.portfolio.allocatedStock.AllocatedStockService;
import com.is442g1t4.gpa.portfolio.allocatedStock.AllocatedStockRepository;

import java.util.Collections;
import java.util.List;
import java.util.*;
import java.util.Optional;

@Service
public class PortfolioCalculatorService {
    @Autowired
    private PortfolioRepository portfolioRepository;

        public List<AllocatedStock> getAllAllocatedStocksInPortfolio(ObjectId id) {

            Portfolio retrievedPortfolio = portfolioRepository.findPortfolioById(id);
            List<AllocatedStock> allocatedStocks = retrievedPortfolio.getAllocatedStocks();
            return allocatedStocks;
        }

        public Map<String, PortfolioCalculator> getCalculatedStockInPortfolio(ObjectId id){
            Map<String, PortfolioCalculator> calculatedStock = new HashMap<>();

            List<AllocatedStock> allocatedStocks = getAllAllocatedStocksInPortfolio(id);

            for (AllocatedStock allocatedStock : allocatedStocks){
                String stockTicker = allocatedStock.getStockTicker();

                if (!calculatedStock.containsKey(stockTicker)){
                    PortfolioCalculator portfolioCalculator = new PortfolioCalculator(stockTicker, allocatedStock.getStockQuantity(), allocatedStock.getStockBuyPrice());
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
            return calculatedStock;
        }
    
}
