package com.is442g1t4.gpa.stock.stockPrice;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "stockPrice")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockPrice {
    @Id
    private ObjectId id;
    private String stockTicker;
    private Date date;
    private double open;
    private double high;
    private double close;
    private double low;
    private double adjustedClose;
    // private long volume;
    // private double dividend;
    // private double splitCoefficient;

    public StockPrice(String stockTicker, Date date, double open, double high, double close, double low,
            double adjustedClose) {
        this.id = new ObjectId(); // Set a new ObjectId internally
        this.stockTicker = stockTicker;
        this.date = date;
        this.open = open;
        this.high = high;
        this.close = close;
        this.low = low;
        this.adjustedClose = adjustedClose;
    }
}
