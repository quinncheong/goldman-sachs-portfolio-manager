package com.is442g1t4.gpa.portfolio.portfolioanalyzer;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is442g1t4.gpa.stock.StockService;
import com.is442g1t4.gpa.stock.model.Stock;
import com.is442g1t4.gpa.user.User;
import com.is442g1t4.gpa.user.UserRepository;
import com.is442g1t4.gpa.portfolio.Portfolio;
import com.is442g1t4.gpa.portfolio.PortfolioRepository;
import com.is442g1t4.gpa.portfolio.PortfolioService;
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
    private PortfolioService portfolioService;

    @Autowired
    private StockService stockService;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public Map<String, Double> getPortfolioAnalysis(ObjectId id) {
        Map<String, Double> result = new HashMap<>();

        List<AllocatedStock> allocatedStocks = portfolioService.getAllAllocatedStocksInPortfolio(id);

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

        dpnl = Double.parseDouble(df.format((value / previousValue - 1) * 100));
        pnl = Double.parseDouble(df.format((value / cost - 1) * 100));
        dpnla = Double.parseDouble(df.format((value - previousValue)));
        pnla = Double.parseDouble(df.format(value - cost));

        result.put("dpnl", Double.parseDouble(df.format(dpnl)));
        result.put("pnl", Double.parseDouble(df.format(pnl)));
        result.put("dpnla", dpnla);
        result.put("pnla", pnla);
        result.put("value",Double.parseDouble(df.format(value)));

        return result;
    }
}
