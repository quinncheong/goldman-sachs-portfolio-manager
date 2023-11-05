package com.is442g1t4.gpa.portfolio.portfolioCalculator;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.is442g1t4.gpa.stock.StockService;
import com.is442g1t4.gpa.user.User;
import com.is442g1t4.gpa.user.UserRepository;
import com.is442g1t4.gpa.stock.model.Stock;
import com.is442g1t4.gpa.portfolio.Portfolio;
import com.is442g1t4.gpa.portfolio.PortfolioRepository;
import com.is442g1t4.gpa.portfolio.PortfolioService;
import com.is442g1t4.gpa.portfolio.allocatedStock.AllocatedStock;
import com.is442g1t4.gpa.portfolio.allocatedStock.AllocatedStockService;
import com.is442g1t4.gpa.portfolio.portfolioanalyzer.PortfolioAnalyzerService;
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
            portfolioCalculator.setPositionsRatio(PortfolioCalculatorUtility.round(portfolioCalculator.getMarket() / totalValue) * 100);
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


}
