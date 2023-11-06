package com.is442g1t4.gpa.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.is442g1t4.gpa.user.UserRepository;

import jakarta.mail.MessagingException;

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
        private final EmailService emailservice;

        public AuthenticationResponse register(RegisterRequest request) {
                User user = User.builder().name(request.getName()).username(request.getUsername())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .email(request.getEmail()).portfolioIds(new ArrayList<ObjectId>())
                                .verified(false).role(RoleEnum.USER).build();
                userRepository.save(user);

                Map<String, Object> extraClaims = new HashMap<>();
                extraClaims.put("userId", user.getId().toString());
                extraClaims.put("role", user.getRole().toString());

                String jwtToken = jwtService.generateRegistrationToken(user.getUsername(),
                                extraClaims);
                try {
                        emailservice.sendVerificationEmail(request.getName(), request.getEmail(),
                                        jwtToken);
                } catch (MessagingException e) {
                        System.out.println("Failed to send email. Error: " + e.getMessage());
                }
                return AuthenticationResponse.builder().token(jwtToken).build();
        }

        public AuthenticationResponse registerAdmin(RegisterRequest request) {
                User user = User.builder().name(request.getName()).username(request.getUsername())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .email(request.getEmail()).portfolioIds(new ArrayList<ObjectId>())
                                .verified(true).role(RoleEnum.ADMIN).build();
                userRepository.save(user);

                String jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder().token(jwtToken).build();
        }

        public AuthenticationResponse authenticate(AuthenticationRequest request)
                        throws ResponseStatusException {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                                request.getUsername(), request.getPassword()));
                // user and password is correct, authenticated
                User user = userRepository.findByUsername(request.getUsername()).orElseThrow();

                Map<String, Object> extraClaims = new HashMap<>();
                extraClaims.put("userId", user.getId().toString());
                extraClaims.put("role", user.getRole().toString());

                String jwtToken = "";
                if (!user.getVerified()) {
                        jwtToken = jwtService.generateRegistrationToken(user.getUsername(),
                                        extraClaims);
                        try {
                                emailservice.sendVerificationEmail(user.getName(), user.getEmail(),
                                                jwtToken);
                                jwtToken = "User is not verified";
                        } catch (MessagingException e) {
                                System.out.println(
                                                "Failed to send email. Error: " + e.getMessage());
                        }
                } else {
                        jwtToken = jwtService.generateToken(user.getUsername(), extraClaims);
                }
                return AuthenticationResponse.builder().token(jwtToken).build();
        }

        public AuthenticationResponse verifyUser(String token) {
                String username = jwtService.extractUsername(token);

                // set user to be verified
                User user = userRepository.findByUsername(username).orElseThrow();
                user.setVerified(true);
                userRepository.save(user);

                Map<String, Object> extraClaims = new HashMap<>();
                extraClaims.put("userId", user.getId().toString());
                extraClaims.put("role", user.getRole().toString());

                String jwtToken = jwtService.generateToken(user.getUsername(), extraClaims);
                return AuthenticationResponse.builder().token(jwtToken).build();
        }

        public boolean verifyToken(String token) {
                return jwtService.isTokenValid(token);
        }

        public EmailResponse sendForgetpwEmail(EmailRequest request) {
                String status = "";

                Optional<User> user = userRepository.findByEmail(request.getEmail());

                if (user.isPresent()) {
                        // A user with the provided email was found, generate jwttoken
                        User foundUser = user.get();
                        Map<String, Object> extraClaims = new HashMap<>();
                        extraClaims.put("userId", foundUser.getId().toString());
                        extraClaims.put("role", foundUser.getRole().toString());

                        String jwtToken = jwtService.generateRegistrationToken(
                                        foundUser.getUsername(), extraClaims);

                        // once jwt token generated, send forget password email
                        try {
                                emailservice.sendForgetPasswordEmail(foundUser.getName(),
                                                foundUser.getEmail(), jwtToken);
                                status = "Forget Password Email Sent";
                        } catch (MessagingException e) {
                                status = "Failed to send email. Error: " + e.getMessage();
                        }

                } else {
                        status = "No user found with email";
                }
                return EmailResponse.builder().emailResponse(status).build();
        }

        public PasswordChangedStatus changeForgottenPassword(ForgetPasswordRequest request,
                        String token) {
                String newPassword = request.getNewPassword();
                String username = jwtService.extractUsername(token);
                User user = userRepository.findByUsername(username).orElseThrow();
                System.out.println(newPassword);
                // set new password
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);

                String status = "Forgotten password successfully changed";
                return PasswordChangedStatus.builder().status(status).build();
        }
}
