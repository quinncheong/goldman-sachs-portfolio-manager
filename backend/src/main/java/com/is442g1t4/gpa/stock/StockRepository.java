package com.is442g1t4.gpa.stock;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.is442g1t4.gpa.stock.model.Stock;

@Repository
public interface StockRepository extends MongoRepository<Stock, String> {
    Optional<Stock> findStockBySymbol(String symbol);
}
