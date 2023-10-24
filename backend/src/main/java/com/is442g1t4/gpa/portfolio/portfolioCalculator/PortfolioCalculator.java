package com.is442g1t4.gpa.portfolio.portfolioCalculator;

import com.is442g1t4.gpa.portfolio.allocatedStock.AllocatedStock;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioCalculator {
    
    private String stockTicker;
    private int stockQuantity;
    private double avgStockBuyPrice;
    private double weight;
    private double currentValue;

}
