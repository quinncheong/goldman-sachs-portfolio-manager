package com.is442g1t4.gpa.stock.stockPrice;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockPriceRepository extends MongoRepository<StockPrice, ObjectId> {
    // Stock addStockPriceData(Stock stock);
    @Aggregation(pipeline = {
            "{ '$match': { 'stockTicker': ?0, 'date' : { $gte : ?1, $lt: ?2 } } }",
            "{ '$limit': 1 }"
    })
    StockPrice findStockPriceByStockTickerAndDate(String stockTicker, Date startDate, Date endDate);

    List<StockPrice> findStockPriceByStockTicker(String stockTicker);

    // List<StockPrice> findStockPriceByDate(Date date);

}
