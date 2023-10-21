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

import com.is442g1t4.gpa.portfolio.allocatedStock.AllocatedStock;
import com.is442g1t4.gpa.portfolio.model.Portfolio;
import com.is442g1t4.gpa.portfolio.portfolioCalculator.PortfolioCalculator;
import com.is442g1t4.gpa.portfolio.service.PortfolioService;
import com.is442g1t4.gpa.portfolio.portfolioanalyzer.PortfolioAnalyzerService;

@RestController
@RequestMapping("/api/v1/portfolio-analyzer")
public class PortfolioAnalyzerController {

    @GetMapping("/{id}")
    public ResponseEntity <Map<String, Object>> getCalculatedStock (@PathVariable ObjectId id){
        return new ResponseEntity<Map<String, Object>>(PortfolioAnalyzerService.getPortfolioAnalysis(id), HttpStatus.OK);
    }
}
