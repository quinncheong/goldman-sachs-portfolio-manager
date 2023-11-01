package com.is442g1t4.gpa.portfolio;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PortfolioRepository extends MongoRepository<Portfolio, ObjectId> {

    Portfolio findPortfolioById(ObjectId id);

    @Query("{'userId': ?0}")
    List<Portfolio> findByUserId(ObjectId userId);
}