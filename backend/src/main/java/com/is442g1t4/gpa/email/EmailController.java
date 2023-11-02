package com.is442g1t4.gpa.email;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.is442g1t4.gpa.auth.EmailDetails;
import com.is442g1t4.gpa.auth.EmailSender;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/v1/email")
public class EmailController {
    @Autowired
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

    EmailSender emailSender = new EmailSender(mailSender);

    @PostMapping("/verification")
    public ResponseEntity<String> sendVerificationEmail(@RequestBody EmailDetails emailDetails){
        String recipientEmail = emailDetails.getRecipient();
        try {
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
