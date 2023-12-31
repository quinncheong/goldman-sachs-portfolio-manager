package com.is442g1t4.gpa.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.is442g1t4.gpa.user.UserRepository;

import jakarta.mail.MessagingException;

import com.is442g1t4.gpa.auth.email.EmailRequest;
import com.is442g1t4.gpa.auth.email.EmailResponse;
import com.is442g1t4.gpa.auth.email.EmailService;
import com.is442g1t4.gpa.auth.utility.ForgetPasswordRequest;
import com.is442g1t4.gpa.auth.utility.PasswordChangedStatus;
import com.is442g1t4.gpa.auth.utility.RegisterRequest;
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

                Optional<User> duplicateUsername =
                                userRepository.findByUsername(request.getUsername());
                Optional<User> duplicateEmail = userRepository.findByEmail(request.getEmail());

                if (duplicateUsername.isPresent() || duplicateEmail.isPresent()) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                        "Duplicate username or email");
                }
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
                User user = userRepository.findByUsername(request.getUsername()).orElseThrow();

                Map<String, Object> extraClaims = new HashMap<>();
                extraClaims.put("userId", user.getId().toString());
                extraClaims.put("role", user.getRole().toString());

                String jwtToken = "";
                boolean verified = false;
                if (!user.getVerified()) {
                        jwtToken = jwtService.generateRegistrationToken(user.getUsername(),
                                        extraClaims);
                        try {
                                emailservice.sendVerificationEmail(user.getName(), user.getEmail(),
                                                jwtToken);
                        } catch (MessagingException e) {
                                System.out.println(
                                                "Failed to send email. Error: " + e.getMessage());
                        }
                } else {
                        jwtToken = jwtService.generateToken(user.getUsername(), extraClaims);
                        verified = true;
                }
                return AuthenticationResponse.builder().token(jwtToken).verified(verified).build();
        }

        public AuthenticationResponse verifyUser(String token) {
                String username = jwtService.extractUsername(token);

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
                        User foundUser = user.get();
                        Map<String, Object> extraClaims = new HashMap<>();
                        extraClaims.put("userId", foundUser.getId().toString());
                        extraClaims.put("role", foundUser.getRole().toString());

                        String jwtToken = jwtService.generateRegistrationToken(
                                        foundUser.getUsername(), extraClaims);

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
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);

                String status = "Forgotten password successfully changed";
                return PasswordChangedStatus.builder().status(status).build();
        }
}
