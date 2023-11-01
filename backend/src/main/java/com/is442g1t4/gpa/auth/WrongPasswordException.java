package com.is442g1t4.gpa.auth;

public class WrongPasswordException extends RuntimeException{
    // Default constructor that sets the default message
    public WrongPasswordException() {
        super("Password does not match");
    }

    // Constructor that accepts a custom message
    public WrongPasswordException(String message) {
        super(message);
    }
}
