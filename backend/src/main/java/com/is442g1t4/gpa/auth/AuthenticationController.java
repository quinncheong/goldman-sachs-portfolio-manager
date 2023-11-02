package com.is442g1t4.gpa.auth;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
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

    @Autowired
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

    EmailSender emailSender = new EmailSender(mailSender);

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/verification")
    public ResponseEntity<String> sendVerificationEmail(@RequestBody EmailDetails emailDetails){
        String recipientEmail = emailDetails.getRecipient();
        // String subject = emailDetails.getSubject();
        // String content = emailDetails.getMsgBody();
        // String attachment = emailDetails.getAttachment();
        try {
            System.out.println("Sending...");
            emailSender.sendEmail(recipientEmail);
            System.out.println("Email sent successfully");
            return new ResponseEntity<String>("Email sent succesfully",
                HttpStatus.OK);
        }
        catch (MessagingException | UnsupportedEncodingException e) {
            System.out.println("Failed to send email. Error: " + e.getMessage());
            return new ResponseEntity<String>("Failed to send email. Error: " + e.getMessage(),
                HttpStatus.OK);
        }
    }
}
