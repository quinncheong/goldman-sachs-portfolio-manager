package com.is442g1t4.gpa.auth;

import java.io.UnsupportedEncodingException;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailSender {
    private final JavaMailSender mailSender;

    public EmailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String email) throws MessagingException, UnsupportedEncodingException {
        System.out.println("Creating email...");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        // MimeMessageHelper helper = new MimeMessageHelper(message);

        String subject = "Email Verification";
        String content = "<h2>Hi this is a test email</h2>";
        String attachment = "";

        helper.setFrom("is442g1t4@gmail.com", "Goldman Sachs");
        helper.setTo(email);

        helper.setSubject(subject);
        helper.setText(content, true);

        System.out.println("formulating email...");

        mailSender.send(message);

        System.out.println("Mail sent");
    }
}
