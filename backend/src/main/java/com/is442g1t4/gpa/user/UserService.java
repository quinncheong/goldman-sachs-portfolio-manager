package com.is442g1t4.gpa.user;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(ObjectId id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public String deleteUser(ObjectId id) {
        userRepository.deleteById(id);
        return "User deleted";
    }

    public User addPortfolioToUser(ObjectId id, ObjectId portfolioId) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()){
            user.get().getPortfolioIds().add(portfolioId);
        }

        return userRepository.save(user.get());
    }
}
