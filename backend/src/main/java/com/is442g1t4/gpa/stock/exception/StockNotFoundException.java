package com.is442g1t4.gpa.stock.exception;

public class StockNotFoundException extends RuntimeException {
    public StockNotFoundException(String message) {
        super(message);
    }

    public StockNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
