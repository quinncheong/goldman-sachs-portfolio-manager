package com.is442g1t4.gpa.portfolio.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "portfolio")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Portfolio {
    @Id
    private ObjectId id;
    private String stockTicker;
    private int stockQuantity;
    private Double purchasePrice;
    private Double stockCurrPrice;
    private String portfolioDescription;
    private Double portfolioAssetValue;
    private Double portfolioDailyPL;

}
