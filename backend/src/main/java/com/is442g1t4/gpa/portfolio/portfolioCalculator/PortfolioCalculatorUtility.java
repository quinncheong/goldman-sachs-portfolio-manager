package com.is442g1t4.gpa.portfolio.portfolioCalculator;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class PortfolioCalculatorUtility {
    public static void setProfitsAndLosses(Instant instant, PortfolioCalculator portfolioCalculator, double quantity, int state){
        if(instant.atZone(ZoneId.systemDefault()).toLocalDate().equals(LocalDate.now()) ){
            if(state == 1){
                portfolioCalculator.setPnl(quantity);
            } else {
                portfolioCalculator.setPnl(portfolioCalculator.getPnl() + quantity);
            }
        } else {
            if(state == 1){
                portfolioCalculator.setDpnl(quantity);
            } else {
                portfolioCalculator.setDpnl(portfolioCalculator.getDpnl() + quantity);
            }
            
        }
    }
}
