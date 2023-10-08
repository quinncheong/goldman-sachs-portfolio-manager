package com.is442g1t4.gpa.stock;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends MongoRepository<Stock, ObjectId> {
    Optional<Stock> findStockByStockTicker(String stockTicker);

    // Stock addStockPriceData(Stock stock);
}
