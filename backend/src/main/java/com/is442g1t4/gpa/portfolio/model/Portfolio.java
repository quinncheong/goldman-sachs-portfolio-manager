package com.is442g1t4.gpa.portfolio.model;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

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
    private ObjectId userId;

    private String name;
    private String description;
    private double initialValue;
    private List<AllocatedStock> allocatedStocks;

    @Data
    public static class AllocatedStock {
        private String stockId;
        private int quantity;
    }
}