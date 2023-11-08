package com.is442g1t4.gpa.portfolio.allocatedStock;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "allocatedStock")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllocatedStock {
    @Id
    private ObjectId id;
    private String stockTicker;
    private int stockQuantity;
    private double stockBuyPrice;
    private Date stockBuyDate;
}
