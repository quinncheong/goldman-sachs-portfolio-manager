package com.is442g1t4.gpa.portfolio.portfolioanalyzer;

import java.util.Map;

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

import com.is442g1t4.gpa.portfolio.Portfolio;
import com.is442g1t4.gpa.portfolio.PortfolioService;
import com.is442g1t4.gpa.portfolio.allocatedStock.AllocatedStock;
import com.is442g1t4.gpa.portfolio.portfolioCalculator.PortfolioCalculator;
import com.is442g1t4.gpa.portfolio.portfolioCalculator.PortfolioCalculatorService;
import com.is442g1t4.gpa.portfolio.portfolioanalyzer.PortfolioAnalyzerService;

@RestController
@RequestMapping("/api/v1/portfolio-analyzer")
public class PortfolioAnalyzerController {

    @Autowired
    private PortfolioAnalyzerService portfolioAnalyzer;

    @Autowired
    private PortfolioCalculatorService portfolioCalculatorService;

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Double>> getPortfolioAnalysis(@PathVariable ObjectId id) {
        return new ResponseEntity<Map<String, Double>>(portfolioAnalyzer.getPortfolioAnalysis(id), HttpStatus.OK);
    }

}
