package com.is442g1t4.gpa.stock.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "stock")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class Stock {
    @Id
    public String symbol;
    public String assetType;
    public String name;
    public String description;
    public String CIK;
    public String exchange;
    public String currency;
    public String country;
    public String sector;
    public String industry;
    public String address;
    public String fiscalYearEnd;
    public String latestQuarter;
    public Long marketCapitalization;
}
