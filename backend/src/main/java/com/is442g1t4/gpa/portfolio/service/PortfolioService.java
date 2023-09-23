package com.is442g1t4.gpa.portfolio.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is442g1t4.gpa.portfolio.repository.PortfolioRepository;
import com.is442g1t4.gpa.portfolio.model.Portfolio;

import java.util.List;
import java.util.Optional;

@Service
public class PortfolioService {
    @Autowired
    private PortfolioRepository portfolioRepository;
    public List<Portfolio> getAllPortfolios() {
        return portfolioRepository.findAll();
    }

    public Optional<Portfolio> getPortfolio(ObjectId id) {
        return portfolioRepository.findById(id);
    }
}
