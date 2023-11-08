package com.is442g1t4.gpa.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.is442g1t4.gpa.auth.email.EmailDetails;
import com.is442g1t4.gpa.auth.email.EmailRequest;
import com.is442g1t4.gpa.auth.email.EmailResponse;
import com.is442g1t4.gpa.auth.email.EmailService;
import com.is442g1t4.gpa.auth.utility.ForgetPasswordRequest;
import com.is442g1t4.gpa.auth.utility.PasswordChangedStatus;
import com.is442g1t4.gpa.auth.utility.RegisterRequest;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final EmailService emailservice;

    @GetMapping("/verify/{token}")
    public ResponseEntity<Boolean> getJwtValidity(@PathVariable String token) {
        return ResponseEntity.ok(authenticationService.verifyToken(token));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/register/admin")
    public ResponseEntity<AuthenticationResponse> registerAdmin(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.registerAdmin(request));
    }

    @PostMapping("/register/verification")
    public ResponseEntity<String> sendVerificationEmail(@RequestBody EmailDetails emailDetails) {
        String recipientEmail = emailDetails.getRecipient();
        String token = "hello I am a jwt token";
        String name = "Bryan";
        try {
            System.out.println("Sending...");
            emailservice.sendVerificationEmail(name, recipientEmail, token);
            System.out.println("Email sent successfully");
            return new ResponseEntity<String>("Email sent succesfully", HttpStatus.OK);
        } catch (MessagingException e) {
            System.out.println("Failed to send email. Error: " + e.getMessage());
            return new ResponseEntity<String>("Failed to send email. Error: " + e.getMessage(),
                    HttpStatus.OK);
        }
    }

    @PostMapping("/register/verification/{token}")
    public ResponseEntity<AuthenticationResponse> verifyAccount(@PathVariable String token) {
        return new ResponseEntity<AuthenticationResponse>(authenticationService.verifyUser(token),
                HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/password/reset/email")
    public ResponseEntity<EmailResponse> forgetPassword(@RequestBody EmailRequest request) {
        return ResponseEntity.ok(authenticationService.sendForgetpwEmail(request));
    }

    @PostMapping("/password/reset/{token}")
    public ResponseEntity<PasswordChangedStatus> changeForgottenPassword(@PathVariable String token,
            @RequestBody ForgetPasswordRequest request) {
        return ResponseEntity.ok(authenticationService.changeForgottenPassword(request, token));
    }

}
