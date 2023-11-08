package com.is442g1t4.gpa.portfolio.portfolioCalculator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioCalculator {

    private String stockTicker;
    private String stockName;
    private String country;
    private int position;
    private double market;
    private double last;
    private double cost;
    private double pnlp;
    private double pnla;
    private double dpnlp;
    private double dpnla;
    private double positionsRatio;

    public PortfolioCalculator(String stockTicker, int position, double cost) {
        this.stockTicker = stockTicker;
        this.position = position;
        this.cost = cost;
    }

}
