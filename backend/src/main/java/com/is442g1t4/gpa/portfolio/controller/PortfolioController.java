package com.is442g1t4.gpa.portfolio.controller;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.is442g1t4.gpa.portfolio.model.Portfolio;
import com.is442g1t4.gpa.portfolio.service.PortfolioService;

@RestController
@RequestMapping("/api/v1/portfolio")
public class PortfolioController {
    @Autowired
    private PortfolioService portfolioService;

    @GetMapping("/all")
    public ResponseEntity<List<Portfolio>> getAllPortfolios() {
        return new ResponseEntity<List<Portfolio>>(portfolioService.getAllPortfolios(),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Portfolio>> getPortfolioByPortfolioId(@PathVariable ObjectId id) {
        return new ResponseEntity<Optional<Portfolio>>(portfolioService.getPortfolioByPortfolioId(id),
                HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Portfolio>> GetAllPortfoliosByUserId(@PathVariable ObjectId userId) {
        return new ResponseEntity<List<Portfolio>>(portfolioService.getPortfoliosByUserId(userId),
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

    @PutMapping("/{id}")
    public ResponseEntity<Portfolio> updatePortfolio(@RequestBody Portfolio portfolio) {
        return new ResponseEntity<Portfolio>(portfolioService.updatePortfolio(portfolio),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePortfolioByPortfolioId(@PathVariable ObjectId id) {
        return new ResponseEntity<String>(portfolioService.deletePortfolioByPortfolioId(id),
                HttpStatus.OK);
    }

    @DeleteMapping("/stock/{portfolioId}")
    public ResponseEntity<Portfolio> clearPortfolio(@PathVariable ObjectId portfolioId) {
        return new ResponseEntity<Portfolio>(portfolioService.delStocksFromPortfolio(portfolioId),
                HttpStatus.OK);
    }
    
    @PutMapping("/{allocatedStockId}/{portfolioId}")
    public ResponseEntity<Portfolio> addStock(@PathVariable ObjectId allocatedStockId,
            @PathVariable ObjectId portfolioId) {
    
        return new ResponseEntity<Portfolio>(portfolioService.addStockToPortfolio(allocatedStockId,portfolioId),
                HttpStatus.OK);

    }
}
