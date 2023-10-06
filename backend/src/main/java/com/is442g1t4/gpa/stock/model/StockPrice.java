package com.is442g1t4.gpa.stock.model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
// import javax.persistence.Column;
// import javax.persistence.Entity;
// import javax.persistence.EnumType;
// import javax.persistence.Enumerated;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.JoinColumn;
// import javax.persistence.ManyToOne;
// import javax.persistence.Table;
// import javax.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

// import org.springframework.data.annotation.Id;

// import com.fasterxml.jackson.annotation.JsonFormat;import org.springframework.data.annotation.Id;

// @Entity
// @Table(name = "stock")
@Document(collection = "stockPrice")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockPrice {
    @Id
    private ObjectId id;
    private String stockTicker;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;
    private double open;
    private double high;
    private double close;
    private double low;
    private double adjustedClose;
    // private long volume;
    // private double dividend;
    // private double splitCoefficient;
}



