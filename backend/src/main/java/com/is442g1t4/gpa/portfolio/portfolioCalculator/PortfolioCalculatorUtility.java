package com.is442g1t4.gpa.portfolio.portfolioCalculator;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class PortfolioCalculatorUtility {

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static Double round(double num){
        return Double.parseDouble(df.format(num));
    }

    public static void setProfitsAndLosses(Instant instant, PortfolioCalculator portfolioCalculator, double quantity, int state){
        if(instant.atZone(ZoneId.systemDefault()).toLocalDate().equals(LocalDate.now()) ){
            if(state == 1){
                portfolioCalculator.setPnlp(quantity);
            } else {
                portfolioCalculator.setPnlp(portfolioCalculator.getPnlp() + quantity);
            }
        } else {
            if(state == 1){
                portfolioCalculator.setDpnlp(quantity);
            } else {
                portfolioCalculator.setDpnlp(portfolioCalculator.getDpnlp() + quantity);
            }
            
        }
    }

}
