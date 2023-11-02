package com.is442g1t4.gpa.portfolio.portfolioCalculator;

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
