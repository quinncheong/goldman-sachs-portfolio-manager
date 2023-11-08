package com.is442g1t4.gpa.portfolio;

import java.util.*;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.is442g1t4.gpa.portfolio.allocatedStock.AllocatedStock;
import com.is442g1t4.gpa.portfolio.allocatedStock.AllocatedStockService;
import com.is442g1t4.gpa.portfolio.portfolioCalculator.PortfolioCalculator;
import com.is442g1t4.gpa.portfolio.portfolioCalculator.PortfolioCalculatorService;
import com.is442g1t4.gpa.portfolio.portfolioanalyzer.PortfolioAnalyzerService;

@RestController
@RequestMapping("/api/v1/portfolio")
public class PortfolioController {
    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private PortfolioCalculatorService portfolioCalculatorService;

    @Autowired
    private PortfolioAnalyzerService portfolioAnalyzer;
    
    @GetMapping("/all")
    public ResponseEntity<List<Portfolio>> getAllPortfolios() {
        return new ResponseEntity<List<Portfolio>>(portfolioService.getAllPortfolios(),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Portfolio>> getPortfolioByPortfolioId(@PathVariable String id) {
        return new ResponseEntity<Optional<Portfolio>>(portfolioService.getPortfolioByPortfolioId(new ObjectId(id)),
                HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Portfolio>> getAllPortfoliosByUserId(@PathVariable String userId) {
        return new ResponseEntity<List<Portfolio>>(portfolioService.getPortfoliosByUserId(new ObjectId(userId)),
                HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> createPortfolio(@RequestBody Portfolio portfolio) {

        Portfolio createdPortfolio = portfolioService.createPortfolio(portfolio);
        if (createdPortfolio == null) {
            return new ResponseEntity<String>("Portfolio Already Exists",
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Portfolio>(createdPortfolio, HttpStatus.OK);

    }

    @PutMapping("/")
    public ResponseEntity<Portfolio> updatePortfolio(@RequestBody Portfolio portfolio) {
        return new ResponseEntity<Portfolio>(portfolioService.updatePortfolio(portfolio),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePortfolioByPortfolioId(@PathVariable String id) {
        return new ResponseEntity<String>(portfolioService.deletePortfolioByPortfolioId(new ObjectId(id)),
                HttpStatus.OK);
    }

    @DeleteMapping("/stock/{portfolioId}")
    public ResponseEntity<Portfolio> clearPortfolio(@PathVariable ObjectId portfolioId) {
        return new ResponseEntity<Portfolio>(portfolioService.delStocksFromPortfolio(portfolioId),
                HttpStatus.OK);
    }

    @PutMapping("/{portfolioId}/{symbol}/{quantity}")
    public ResponseEntity<Portfolio> addStock(@PathVariable ObjectId portfolioId, @PathVariable String symbol,
            @PathVariable int quantity) {

        return new ResponseEntity<Portfolio>(portfolioService.addStockToPortfolio(symbol, quantity, portfolioId),
                HttpStatus.OK);
    }

    @GetMapping("/allocatedStock/{id}")
    public ResponseEntity<List<AllocatedStock>> getAllocatedStocks(@PathVariable ObjectId id) {
        return new ResponseEntity<List<AllocatedStock>>(portfolioService.getAllAllocatedStocksInPortfolio(id),
                HttpStatus.OK);
    }

    @PostMapping("/addAllocatedStock/{portfolioId}/{userId}")
    public ResponseEntity<Portfolio> createAllocatedStock(@RequestBody AllocatedStock allocatedStock,
            @PathVariable ObjectId portfolioId, @PathVariable ObjectId userId) {
        Portfolio updatedPortfolio = portfolioService.addAllocatedStock(allocatedStock, portfolioId, userId);

        if (updatedPortfolio == null) {
            return new ResponseEntity<Portfolio>(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(updatedPortfolio);
    }


    @GetMapping("/{id}/stock")
    public ResponseEntity<Map<String, PortfolioCalculator>> getCalculatedStock(@PathVariable ObjectId id) {
        return new ResponseEntity<Map<String, PortfolioCalculator>>(
                portfolioCalculatorService.getCalculatedStockInPortfolio(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/stockData")
    public ResponseEntity<Map<String, Map<String,Double>>> getStockData(@PathVariable ObjectId id) {
        return new ResponseEntity<Map<String, Map<String,Double>>>(
                portfolioCalculatorService.getStockData(portfolioCalculatorService.getCalculatedStockInPortfolio(id)), HttpStatus.OK);
    }
    @GetMapping("/{id}/portfolio")
    public ResponseEntity<Map<String, Double>> getPortfolioTotal(@PathVariable ObjectId id) {
        return new ResponseEntity<Map<String, Double>>(portfolioAnalyzer.getPortfolioAnalysis(id), HttpStatus.OK);
    }

    @DeleteMapping("/{portfolioId}/{stockTicker}")
    public ResponseEntity<Portfolio> deleteAllocatedStockFromPortfolio(@PathVariable ObjectId portfolioId, @PathVariable String stockTicker) {

        Portfolio retrievedPortfolio = portfolioService.deleteAllocatedStockFromPortfolio(portfolioId, stockTicker);

        return new ResponseEntity<Portfolio>(retrievedPortfolio, HttpStatus.OK);
        
    }

    @GetMapping("/ror/{id}")
    public ResponseEntity<Double> getRoR(@PathVariable ObjectId id) {
        Map<String, PortfolioCalculator> calculated = portfolioCalculatorService.getCalculatedStockInPortfolio(id);
        return new ResponseEntity<Double>(
            portfolioService.getPortfolioStockExAndVar(calculated), HttpStatus.OK);
    }

    @GetMapping("/timely/{id}/{startDate}/{endDate}")
    public ResponseEntity<Map<String, Double>> getTimely(@PathVariable ObjectId id, 
                                                        @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
                                                        @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate){
        Map<String, PortfolioCalculator> calculated = portfolioCalculatorService.getCalculatedStockInPortfolio(id);
        Map<String, Double> result = portfolioService.getMonthlyPortfolioValueByDateRange(calculated, startDate, endDate);
        return new ResponseEntity<Map<String, Double>>(result, HttpStatus.OK);
    }
}
