package com.is442g1t4.gpa.portfolio.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is442g1t4.gpa.portfolio.repository.PortfolioRepository;
import com.is442g1t4.gpa.user.User;
import com.is442g1t4.gpa.user.UserRepository;
import com.is442g1t4.gpa.portfolio.model.Portfolio;
import com.is442g1t4.gpa.portfolio.allocatedStock.AllocatedStock;
import com.is442g1t4.gpa.portfolio.allocatedStock.AllocatedStockService;
import com.is442g1t4.gpa.portfolio.allocatedStock.AllocatedStockRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PortfolioService {
    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AllocatedStockRepository allocatedStockRepository;

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

    public Portfolio addStockToPortfolio(ObjectId allocatedStockId, ObjectId portfolioId) {
        Optional<Portfolio> portfolio = portfolioRepository.findById(portfolioId);
        Optional<AllocatedStock> allocatedStock = allocatedStockRepository.findById(allocatedStockId);
        if (portfolio.isPresent()) {
            portfolio.get().getAllocatedStocks().add(allocatedStock.get());
        }
        return portfolioRepository.save(portfolio.get());
    }

    public Portfolio delStocksFromPortfolio(ObjectId portfolioId) {
        Optional<Portfolio> portfolio = portfolioRepository.findById(portfolioId);
        if (portfolio.isPresent()) {
            portfolio.get().setAllocatedStocks(Collections.<AllocatedStock>emptyList());
            return portfolioRepository.save(portfolio.get());
        }
        return null;

    }

    public List<AllocatedStock> getAllAllocatedStocksInPortfolio(ObjectId id) {

        Portfolio retrievedPortfolio = portfolioRepository.findPortfolioById(id);
        List<AllocatedStock> allocatedStocks = retrievedPortfolio.getAllocatedStocks();
        return allocatedStocks;
    }
}
