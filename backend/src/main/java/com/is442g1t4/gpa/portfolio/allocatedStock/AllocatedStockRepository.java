package com.is442g1t4.gpa.portfolio.allocatedStock;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllocatedStockRepository extends MongoRepository<AllocatedStock, ObjectId>{
    
    List<AllocatedStock> findAllocatedStockByStockTicker(String stockTicker);

    AllocatedStock findAllocatedStockById(String id);

}
