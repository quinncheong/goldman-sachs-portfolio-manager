package com.is442g1t4.gpa.stock.stockPrice;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StockPriceRepository extends MongoRepository<StockPrice, ObjectId> {
    // Stock addStockPriceData(Stock stock);

    @Query("{ 'stockTicker' : ?0, 'date' : ?1 }")
    StockPrice findStockPriceByStockTickerAndDate(String stockTicker, Date date);
    

    List<StockPrice> findStockPriceByStockTicker(String stockTicker);

    // List<StockPrice> findStockPriceByDate(Date date);
    
}
