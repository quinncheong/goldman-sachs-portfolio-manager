package com.is442g1t4.gpa.stock.controller;

import org.springframework.beans.factory.annotation.Autowired;

// import org.modelmapper.ModelMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import com.is442g1t4.gpa.stock.model.Stock;
import com.is442g1t4.gpa.stock.model.StockPrice;
import com.is442g1t4.gpa.stock.service.StockPriceService;
import com.is442g1t4.gpa.stock.service.StockService;

@RestController
@RequestMapping("/api/v1/stock-price")
public class StockPriceController{
    
    @Autowired
    private StockPriceService stockPriceService;

    @PostMapping("/")
    public ResponseEntity<?> createStockPrice(@RequestBody StockPrice stockPrice){
        StockPrice savedStockPrice = stockPriceService.addStockPrice(stockPrice);
        if(savedStockPrice == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Stock Price already exists");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStockPrice);
    }


}