package com.is442g1t4.gpa.stock.repository;

import java.util.Date;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.is442g1t4.gpa.stock.model.StockPrice;

@Repository
public interface StockPriceRepository extends MongoRepository<StockPrice, ObjectId> {
    // Stock addStockPriceData(Stock stock);

    @Query("{ 'stockTicker' : ?0, 'date' : ?1 }")
    StockPrice findStockPriceByStockTickerAndDate(String stockTicker, Date date);
}
