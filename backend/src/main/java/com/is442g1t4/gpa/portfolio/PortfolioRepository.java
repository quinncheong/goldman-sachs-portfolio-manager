package com.is442g1t4.gpa.portfolio;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends MongoRepository<Portfolio, ObjectId> {

    Portfolio findPortfolioById(ObjectId id);
}