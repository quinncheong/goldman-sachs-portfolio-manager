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
import java.util.ArrayList;
import java.util.Optional;

@Service
public class AuthService implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User foundUser = userRepository.findByUsername(username);
        if (foundUser == null) return null;
        System.out.println(foundUser);
        ObjectId id = foundUser.getId();
        String name = foundUser.getName();
        String currusername = foundUser.getUsername();
        String pwd = foundUser.getPassword();
        String email = foundUser.getEmail();
        List<ObjectId> portfolioIds = foundUser.getPortfolioIds();
        
        // return new User(id,name,currusername,pwd,email,portfolioIds);
        return new User(currusername,pwd, new ArrayList<>());
    }

}
