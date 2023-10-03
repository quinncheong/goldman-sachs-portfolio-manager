package com.is442g1t4.gpa.portfolio.controller;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<Optional<Portfolio>> getPortfolio(@PathVariable ObjectId id) {
        return new ResponseEntity<Optional<Portfolio>>(portfolioService.getPortfolio(id),
                HttpStatus.OK);
    }
}
