package com.is442g1t4.gpa.stock.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.is442g1t4.gpa.stock.model.Stock;

@Repository
public interface StockRepository extends MongoRepository<Stock, ObjectId>{
    Optional<Stock> findStockByStockTicker(String stockTicker);
}
