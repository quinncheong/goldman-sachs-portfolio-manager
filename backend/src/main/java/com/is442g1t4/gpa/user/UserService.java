package com.is442g1t4.gpa.user;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.is442g1t4.gpa.auth.utility.PasswordRequest;
import com.is442g1t4.gpa.auth.utility.WrongPasswordException;
import com.is442g1t4.gpa.portfolio.Portfolio;
import com.is442g1t4.gpa.portfolio.PortfolioService;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.is442g1t4.gpa.portfolio.portfolioanalyzer.PortfolioAnalyzerService;
import com.is442g1t4.gpa.portfolio.portfolioCalculator.PortfolioCalculatorUtility;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccessLogRepository accessLogRepository;

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private PortfolioAnalyzerService portfolioAnalyzer;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(ObjectId id) {
        System.out.println(id);
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public String deleteUser(ObjectId id) {
        userRepository.deleteById(id);
        return "User deleted";
    }

    public User changeUserPassword(ObjectId id, PasswordRequest request)
            throws WrongPasswordException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            String oldPassword = user.get().getPassword();
            boolean isPasswordMatch =
                    passwordEncoder.matches(request.getOldpassword(), oldPassword);
            if (isPasswordMatch) {
                user.get().setPassword(passwordEncoder.encode(request.getNewpassword()));
            } else {
                throw new WrongPasswordException("Old password does not match");
            }
        }
        return userRepository.save(user.get());

    }

    public List<AccessLog> getAccessLogs(ObjectId userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent() && user.get().getRole() == RoleEnum.ADMIN) {
            return accessLogRepository.findAll();
        }

        return null;
    }

    public AccessLog createAccessLog(AccessLog accessLog) {
        accessLog.setDate(new Date());
        Optional<User> user = userRepository.findById(accessLog.getUserId());
        if (user.isPresent()) {
            accessLog.setUserName(user.get().getUsername());
        }
        return accessLogRepository.save(accessLog);
    }

    public User addPortfolioToUser(ObjectId id, ObjectId portfolioId) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            user.get().getPortfolioIds().add(portfolioId);
        }

        return userRepository.save(user.get());
    }

    public User addPortfolio(ObjectId id, Portfolio portfolio) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            user.get().getPortfolioIds().add(portfolioService.createPortfolio(portfolio).getId());
        }

        return userRepository.save(user.get());
    }

    public User addCash(ObjectId id, double cash) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            user.get().setCashBalance(user.get().getCashBalance() + cash);
        }
        return userRepository.save(user.get());
    }

    public Map<String, Double> getAccountData(ObjectId id) {
        Map<String, Double> data = new HashMap<>();
        Double dpnl = 0.0;
        Double dpnla = 0.0;
        Double pnla = 0.0;
        Double pnl = 0.0;

        Double totalSecurities = 0.0;
        Double totalCash = 0.0;
        Optional<User> user = userRepository.findById(id);

        List<Portfolio> portfolios = portfolioService.getPortfoliosByUserId(id);
        if (portfolios.size() > 0) {
            for (Portfolio portfolio : portfolios) {
                Map<String, Double> result =
                        portfolioAnalyzer.getPortfolioAnalysis(portfolio.getId());
                System.out.println(result);
                dpnl += result.get("dpnl");
                dpnla += result.get("dpnla");
                pnla += result.get("pnla");
                pnl += result.get("pnl");
                totalSecurities += result.get("value");

                // comment out once refactor
                totalCash += portfolio.getInitialValue();
            }
            totalCash += user.get().getCashBalance();

        }
        Double totalAssets = totalCash + totalSecurities;
        data.put("dpnlp", PortfolioCalculatorUtility.round(dpnl));
        data.put("dpnla", dpnla);
        data.put("pnla", pnla);
        data.put("pnlp", PortfolioCalculatorUtility.round(pnl));
        data.put("totalAssets", totalAssets);
        data.put("totalSecurities", totalSecurities);
        data.put("totalCash", totalCash);
        return data;
    }
}
