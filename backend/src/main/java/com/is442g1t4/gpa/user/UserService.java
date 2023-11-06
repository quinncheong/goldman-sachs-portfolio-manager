package com.is442g1t4.gpa.user;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.is442g1t4.gpa.auth.PasswordRequest;
import com.is442g1t4.gpa.auth.WrongPasswordException;
import com.is442g1t4.gpa.portfolio.Portfolio;
import com.is442g1t4.gpa.portfolio.PortfolioService;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.swing.text.html.Option;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccessLogRepository accessLogRepository;

    @Autowired
    private PortfolioService PortfolioService;

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
        System.out.println("Old Password: " + request.getOldpassword());
        System.out.println("New Password: " + request.getNewpassword());
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            String oldPassword = user.get().getPassword();
            boolean isPasswordMatch =
                    passwordEncoder.matches(request.getOldpassword(), oldPassword);
            System.out.println(isPasswordMatch);
            if (isPasswordMatch) {
                System.out.println("Password match, changing password...");
                user.get().setPassword(passwordEncoder.encode(request.getNewpassword()));
                System.out.println("Password changed");
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
            user.get().getPortfolioIds().add(PortfolioService.createPortfolio(portfolio).getId());
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

}
