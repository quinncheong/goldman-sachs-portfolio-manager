package com.is442g1t4.gpa.stock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class StockDetailsResponse {
    public String Symbol;
    public String AssetType;
    public String Name;
    public String Description;
    public String CIK;
    public String Exchange;
    public String Currency;
    public String Country;
    public String Sector;
    public String Industry;
    public String Address;
    public String FiscalYearEnd;
    public String LatestQuarter;
    public Long MarketCapitalization;
    public Long EBITDA;
    public Double PERatio;
    public Double PEGRatio;
    public Double BookValue;
    public Double DividendPerShare;
    public Double DividendYield;
    public Double EPS;
    public Double RevenuePerShareTTM;
    public Double ProfitMargin;
    public Double OperatingMarginTTM;
    public Double ReturnOnAssetsTTM;
    public Double ReturnOnEquityTTM;
    public Long RevenueTTM;
    public Long GrossProfitTTM;
    public Double DilutedEPSTTM;
    public Double QuarterlyEarningsGrowthYOY;
    public Double QuarterlyRevenueGrowthYOY;
    public Double AnalystTargetPrice;
    public Double TrailingPE;
    public Double ForwardPE;
    public Double PriceToSalesRatioTTM;
    public Double PriceToBookRatio;
    public Double EVToRevenue;
    public Double EVToEBITDA;
    public Double Beta;
    public Double _52WeekHigh;
    public Double _52WeekLow;
    public Double _50DayMovingAverage;
    public Double _200DayMovingAverage;
    public Long SharesOutstanding;
    public String DividendDate;
    public String ExDividendDate;
}
