package com.is442g1t4.gpa.portfolio;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is442g1t4.gpa.user.User;
import com.is442g1t4.gpa.user.UserRepository;
import com.is442g1t4.gpa.portfolio.PortfolioService;
import com.is442g1t4.gpa.portfolio.allocatedStock.AllocatedStock;
import com.is442g1t4.gpa.portfolio.allocatedStock.AllocatedStockService;
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
    private AllocatedStockService allocatedStockService;

    public List<Portfolio> getAllPortfolios() {
        return portfolioRepository.findAll();
    }

    public Optional<Portfolio> getPortfolioByPortfolioId(ObjectId id) {
        return portfolioRepository.findById(id);
    }

    public List<Portfolio> getPortfoliosByUserId(ObjectId userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return portfolioRepository.findByUserId(user.get().getId());
        } else {
            
            return Collections.<Portfolio>emptyList();
        }
    }

    public Portfolio createPortfolio(Portfolio portfolio) {
        if (portfolio.getId() != null) {
            return null;
        }
        portfolio.setId(new ObjectId());
        return portfolioRepository.save(portfolio);
    }

    public Portfolio updatePortfolio(Portfolio portfolio) {
        return portfolioRepository.save(portfolio);
    }

    public String deletePortfolioByPortfolioId(ObjectId id) {
        portfolioRepository.deleteById(id);
        return "Portfolio Deleted";
    }

    // public Portfolio oldaddStockToPortfolio(ObjectId allocatedStockId, ObjectId
    // portfolioId) {
    // Optional<Portfolio> portfolio = portfolioRepository.findById(portfolioId);
    // Optional<AllocatedStock> allocatedStock =
    // allocatedStockRepository.findById(allocatedStockId);
    // if (portfolio.isPresent()) {
    // portfolio.get().getAllocatedStocks().add(allocatedStock.get());
    // }
    // return portfolioRepository.save(portfolio.get());
    // }
    public Portfolio addStockToPortfolio(String symbol, int quantity, ObjectId portfolioId) {
        Optional<Portfolio> portfolio = portfolioRepository.findById(portfolioId);
        if (portfolio.isPresent()) {
            portfolio.get().getAllocatedStocks().add(allocatedStockService.addAllocatedStock(symbol, quantity));
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

    public Portfolio addAllocatedStock(AllocatedStock allocatedStock, ObjectId portfolioId, ObjectId userId) {

        AllocatedStock savedAllocatedStock = allocatedStockService.addAllocatedStock(allocatedStock);

        double allocatedStockValue = 0.0;
        double userCashBalance = 0.0;
        Optional<Portfolio> portfolio = portfolioRepository.findById(portfolioId);
        if (portfolio.isPresent()) {

            Optional<User> user = userRepository.findById(userId);
            if (user.isPresent()) {
                allocatedStockValue = savedAllocatedStock.getStockQuantity() * savedAllocatedStock.getStockBuyPrice();

                userCashBalance = user.get().getCashBalance();
                if (userCashBalance >= allocatedStockValue) {

                    user.get().setCashBalance(userCashBalance - allocatedStockValue);
                    userRepository.save(user.get());
                    userCashBalance = user.get().getCashBalance();

                    portfolio.get().getAllocatedStocks().add(savedAllocatedStock);
                } else {

                    return null;
                }
            }
        }
        return portfolioRepository.save(portfolio.get());
    }

}
