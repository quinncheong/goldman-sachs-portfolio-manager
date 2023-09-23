package com.is442g1t4.gpa.portfolio.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.is442g1t4.gpa.portfolio.model.Portfolio;

@Repository
public interface PortfolioRepository extends MongoRepository<Portfolio, ObjectId>{
}