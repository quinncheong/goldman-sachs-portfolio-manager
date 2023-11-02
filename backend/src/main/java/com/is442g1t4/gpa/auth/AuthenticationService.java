package com.is442g1t4.gpa.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.is442g1t4.gpa.user.UserRepository;
import com.is442g1t4.gpa.config.JwtService;
import com.is442g1t4.gpa.user.RoleEnum;
import com.is442g1t4.gpa.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;

        public AuthenticationResponse register(RegisterRequest request) {
                User user = User.builder().name(request.getName()).username(request.getUsername())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .email(request.getEmail()).portfolioIds(new ArrayList<ObjectId>())
                                .role(RoleEnum.USER).build();
                userRepository.save(user);

                String jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder().token(jwtToken).build();
        }

        public AuthenticationResponse authenticate(AuthenticationRequest request) {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                                request.getUsername(), request.getPassword()));
                // user and password is correct, authenticated
                User user = userRepository.findByUsername(request.getUsername()).orElseThrow();

                Map<String, Object> extraClaims = new HashMap<>();
                extraClaims.put("userId", user.getId().toString());
                extraClaims.put("role", user.getRole().toString());

                String jwtToken = jwtService.generateToken(user.getUsername(), extraClaims);
                return AuthenticationResponse.builder().token(jwtToken).build();
        }

}
