package com.is442g1t4.gpa.portfolio.portfolioanalyzer;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is442g1t4.gpa.portfolio.repository.PortfolioRepository;
import com.is442g1t4.gpa.portfolio.service.PortfolioService;
import com.is442g1t4.gpa.stock.StockService;
import com.is442g1t4.gpa.stock.model.Stock;
import com.is442g1t4.gpa.user.User;
import com.is442g1t4.gpa.user.UserRepository;
import com.is442g1t4.gpa.portfolio.model.Portfolio;
import com.is442g1t4.gpa.portfolio.allocatedStock.AllocatedStock;
import com.is442g1t4.gpa.portfolio.allocatedStock.AllocatedStockService;
import com.is442g1t4.gpa.portfolio.allocatedStock.AllocatedStockRepository;

import java.util.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.Instant;

@Service
public class PortfolioAnalyzerService {

    @Autowired
    private static PortfolioService portfolioService;

    @Autowired
    private static StockService stockService;

    public static Map<String, Object> getPortfolioAnalysis(ObjectId id){
        Map<String, Object> result = new HashMap<>();
        DecimalFormat df = new DecimalFormat("#.##");

        List<AllocatedStock> allocatedStocks = portfolioService.getAllAllocatedStocksInPortfolio(id);

        double cost = 0;
        double value = 0;
        double previousValue = 0;
        double pnl;
        double dpnl;

        for (AllocatedStock allocatedStock : allocatedStocks){
            String stockTicker = allocatedStock.getStockTicker();
            Stock stock = stockService.getStockByTicker(stockTicker).get();
            int quantity = allocatedStock.getStockQuantity();
            cost = cost + (allocatedStock.getStockBuyPrice() * quantity);
            value  = value + (stock.priceToday() * quantity);
            Date buyDate = allocatedStock.getStockBuyDate();
            Instant instant = buyDate.toInstant();
            if(instant.atZone(ZoneId.systemDefault()).toLocalDate().equals(LocalDate.now()) ){
                previousValue = previousValue + (stock.priceToday() * quantity);
            } else {
                previousValue = previousValue + (stock.priceYesterday());
            }
        }

        dpnl = value/previousValue - 1;
        pnl = value/cost - 1;

        result.put("cost", df.format(dpnl));
        result.put("pnl", df.format(pnl));


        return result;
    }
}
