package com.is442g1t4.gpa.user;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.is442g1t4.gpa.portfolio.Portfolio;
import com.is442g1t4.gpa.user.User;
import com.is442g1t4.gpa.user.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<List<User>>(userService.getAllUsers(),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUserByUserId(@PathVariable ObjectId id) {
        return new ResponseEntity<Optional<User>>(userService.getUser(id),
                HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<User>(userService.createUser(user),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable ObjectId id) {
        return new ResponseEntity<String>(userService.deleteUser(id),
                HttpStatus.OK);
    }

    @PutMapping("/{id}/{portfolioId}")
    public ResponseEntity<User> addPortfolioToUser(@PathVariable ObjectId id, @PathVariable ObjectId portfolioId) {
        return new ResponseEntity<User>(userService.addPortfolioToUser(id, portfolioId),
                HttpStatus.OK);
    }

    @PutMapping("/portfolio/{id}")
    public ResponseEntity<User> addPortfolio(@PathVariable ObjectId id, Portfolio portfolio) {
        return new ResponseEntity<User>(userService.addPortfolio(id, portfolio),
                HttpStatus.OK);
    }

    @PutMapping("/cash/{id}/{cash}")
    public ResponseEntity<User> addCash(@PathVariable ObjectId id, @PathVariable double cash) {
        return new ResponseEntity<User>(userService.addCash(id, cash),
                HttpStatus.OK);
    }
}
