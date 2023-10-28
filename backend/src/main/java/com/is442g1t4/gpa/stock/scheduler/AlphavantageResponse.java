package com.is442g1t4.gpa.stock.scheduler;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AlphavantageResponse {
    @JsonProperty("Meta Data")
    private MetaData metaData;

    @JsonProperty("Time Series (Daily)")
    private Map<String, AlphavantageStockPrice> dailyTimeSeries;

    @Data
    class MetaData {
        @JsonProperty("1. Information")
        private String information;

        @JsonProperty("2. Symbol")
        private String symbol;

        @JsonProperty("3. Last Refreshed")
        private String lastRefreshed;

        @JsonProperty("4. Output Size")
        private String outputSize;

        @JsonProperty("5. Time Zone")
        private String timeZone;

    }

    @Data
    class AlphavantageStockPrice {
        @JsonProperty("1. open")
        private double open;

        @JsonProperty("2. high")
        private double high;

        @JsonProperty("3. low")
        private double low;

        @JsonProperty("4. close")
        private double close;

        @JsonProperty("5. adjusted close")
        private double adjustedClose;

        @JsonProperty("6. volume")
        private int volume;

        @JsonProperty("7. dividend amount")
        private double dividendAmount;

        @JsonProperty("8. split coefficient")
        private double splitCoefficient;

    }

}
