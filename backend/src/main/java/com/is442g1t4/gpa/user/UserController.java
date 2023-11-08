package com.is442g1t4.gpa.user;

import java.util.*;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity.AuthorizePayloadsSpec.Access;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.is442g1t4.gpa.auth.utility.PasswordRequest;
import com.is442g1t4.gpa.portfolio.Portfolio;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<List<User>>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUserByUserId(@PathVariable ObjectId id) {
        return new ResponseEntity<Optional<User>>(userService.getUser(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<User>(userService.createUser(user), HttpStatus.OK);
    }

    @PutMapping("/{id}/changepassword")
    public ResponseEntity<User> changeUserPassword(@PathVariable ObjectId id,
            @RequestBody PasswordRequest request) {
        return new ResponseEntity<User>(userService.changeUserPassword(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable ObjectId id) {
        return new ResponseEntity<String>(userService.deleteUser(id), HttpStatus.OK);
    }

    // Access Logging
    @GetMapping("/{userId}/log")
    public ResponseEntity<List<AccessLog>> getAccessLog(@PathVariable String userId) {
        return new ResponseEntity<>(userService.getAccessLogs(new ObjectId(userId)), HttpStatus.OK);
    }

    @PostMapping("/log")
    public ResponseEntity<AccessLog> createAccessLog(@RequestBody AccessLog accessLog) {
        return new ResponseEntity<AccessLog>(userService.createAccessLog(accessLog), HttpStatus.OK);
    }


    @PutMapping("/{id}/{portfolioId}")
    public ResponseEntity<User> addPortfolioToUser(@PathVariable ObjectId id,
            @PathVariable ObjectId portfolioId) {
        return new ResponseEntity<User>(userService.addPortfolioToUser(id, portfolioId),
                HttpStatus.OK);
    }

    @PutMapping("/portfolio/{id}")
    public ResponseEntity<User> addPortfolio(@PathVariable ObjectId id, Portfolio portfolio) {
        return new ResponseEntity<User>(userService.addPortfolio(id, portfolio), HttpStatus.OK);
    }

    @PutMapping("/cash/{id}/{cash}")
    public ResponseEntity<User> addCash(@PathVariable ObjectId id, @PathVariable double cash) {
        return new ResponseEntity<User>(userService.addCash(id, cash), HttpStatus.OK);
    }

    @GetMapping("/data/{id}")
    public ResponseEntity<Map<String, Double>> getAccountData(@PathVariable ObjectId id) {
        return new ResponseEntity<Map<String, Double>>(userService.getAccountData(id),
                HttpStatus.OK);
    }
}
