package com.is442g1t4.gpa.stock.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "trackedStock")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackedStock {
    @Id
    public String symbol;
}
