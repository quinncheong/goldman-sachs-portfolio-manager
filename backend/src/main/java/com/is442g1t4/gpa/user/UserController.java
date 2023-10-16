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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.is442g1t4.gpa.user.User;
import com.is442g1t4.gpa.user.UserService;
import com.is442g1t4.gpa.user.AuthenticationRequest;
import com.is442g1t4.gpa.user.AuthenticationResponse;
import com.is442g1t4.gpa.SecurityConfig;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

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
    // can remove later since same as above
    // @PostMapping("/createnew")
    // private ResponseEntity<?> createUser(@RequestBody AuthenticationRequest
    // authenticationRequest) {
    // String username = authenticationRequest.getUsername();
    // String password = authenticationRequest.getPassword();
    // }

    @PostMapping("/check")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        String username = authenticationRequest.getUsername();
        // String password = authenticationRequest.getPassword();
        return ResponseEntity.ok(userService.loadUserByUsername(username));
    }

    @PostMapping("/auth")
    public ResponseEntity<?> authenticateClient(@RequestBody AuthenticationRequest authenticationRequest) {
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        System.out.println(username);
        System.out.println(password);
        try {
            System.out.println("Start auth...");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            System.out.println("End auth...");
        } catch (Exception e) {
            return ResponseEntity.ok(new AuthenticationResponse(e.getMessage()));
        }
        return ResponseEntity.ok(new AuthenticationResponse("Authentication Successful" + username));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable ObjectId id) {
        return new ResponseEntity<String>(userService.deleteUser(id),
                HttpStatus.OK);
    }
}
