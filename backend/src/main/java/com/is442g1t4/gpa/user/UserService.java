package com.is442g1t4.gpa.user;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.*;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(ObjectId id) {
        return userRepository.findById(id);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User foundUser = userRepository.findByUsername(username);
        if (foundUser == null) return null;
        System.out.println(foundUser);
        ObjectId id = foundUser.getId();
        String name = foundUser.getName();
        String currusername = foundUser.getUsername();
        String pwd = foundUser.getPassword();
        String email = foundUser.getEmail();
        List<ObjectId> portfolioIds = foundUser.getPortfolioIds();
        System.out.println("=======");
        System.out.println(currusername);
        System.out.println(pwd);
        System.out.println(new org.springframework.security.core.userdetails.User(
            currusername,
            pwd,
            new ArrayList<>()
        ).getPassword());
        return new org.springframework.security.core.userdetails.User(
            currusername,
            pwd,
            new ArrayList<>()
        );

        // return new User(id,name,currusername,pwd,email,portfolioIds,new ArrayList<>());
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public String deleteUser(ObjectId id) {
        userRepository.deleteById(id);
        return "User deleted";
    }
}
