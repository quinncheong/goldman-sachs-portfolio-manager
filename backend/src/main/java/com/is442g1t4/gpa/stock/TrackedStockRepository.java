package com.is442g1t4.gpa.stock;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.is442g1t4.gpa.stock.model.TrackedStock;

@Repository
public interface TrackedStockRepository extends MongoRepository<TrackedStock, String> {
}
