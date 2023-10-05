package com.is442g1t4.gpa.portfolio.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is442g1t4.gpa.portfolio.repository.PortfolioRepository;
import com.is442g1t4.gpa.user.User;
import com.is442g1t4.gpa.user.UserRepository;
import com.is442g1t4.gpa.portfolio.model.Portfolio;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PortfolioService {
    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Portfolio> getAllPortfolios() {
        return portfolioRepository.findAll();
    }

    public Optional<Portfolio> getPortfolioByPortfolioId(ObjectId id) {
        return portfolioRepository.findById(id);
    }

    public List<Portfolio> getPortfoliosByUserId(ObjectId userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<ObjectId> portfolioIds = user.get().getPortfolioIds();
            return portfolioRepository.findAllById(portfolioIds);
        } else {
            return Collections.<Portfolio>emptyList();
        }
    }

    public Portfolio createPortfolio(Portfolio portfolio) {
        if (portfolio.getId() != null && portfolioRepository.existsById(portfolio.getId())) {
            return null;
        }
        return portfolioRepository.save(portfolio);
    }

    public Portfolio updatePortfolio(Portfolio portfolio) {
        return portfolioRepository.save(portfolio);
    }

    public String deletePortfolioByPortfolioId(ObjectId id) {
        portfolioRepository.deleteById(id);
        return "Portfolio Deleted";
    }
}
