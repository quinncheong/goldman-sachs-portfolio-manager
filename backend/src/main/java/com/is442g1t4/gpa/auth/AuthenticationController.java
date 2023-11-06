package com.is442g1t4.gpa.auth;

// import java.io.UnsupportedEncodingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final EmailService emailservice;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/registerAdmin")
    public ResponseEntity<AuthenticationResponse> registerAdmin(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.registerAdmin(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/forgetpassword")
    public ResponseEntity<EmailResponse> forgetPassword(@RequestBody EmailRequest request) {
        return ResponseEntity.ok(service.sendForgetpwEmail(request));
    }

    @PostMapping("/changepassword/{token}")
    public ResponseEntity<PasswordChangedStatus> changeForgottenPassword(@PathVariable String token,
            @RequestBody ForgetPasswordRequest request) {
        return ResponseEntity.ok(service.changeForgottenPassword(request, token));
    }

    @PostMapping("/verification")
    public ResponseEntity<String> sendVerificationEmail(@RequestBody EmailDetails emailDetails) {
        String recipientEmail = emailDetails.getRecipient();
        String token = "hello I am a jwt token";
        String name = "Bryan";
        // String subject = emailDetails.getSubject();
        // String content = emailDetails.getMsgBody();
        // String attachment = emailDetails.getAttachment();
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

    @PostMapping("/verification/{token}")
    public ResponseEntity<AuthenticationResponse> verifyAccount(@PathVariable String token) {
        return new ResponseEntity<AuthenticationResponse>(service.verifyUser(token), HttpStatus.OK);
    }
}
