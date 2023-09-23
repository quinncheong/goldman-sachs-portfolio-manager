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

import java.util.List;
import java.util.Optional;

import com.is442g1t4.gpa.stock.model.Stock;
import com.is442g1t4.gpa.stock.service.StockService;

@RestController
@RequestMapping("/api/v1/stock")
public class StockController {
    @Autowired
    private StockService stockService;

    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        return new ResponseEntity<List<Stock>>(stockService.getAllStocks(), HttpStatus.OK);
    }

    @GetMapping("/{stockTicker}")
    public ResponseEntity<Optional<Stock>> getStock(@PathVariable String stockTicker) {
        return new ResponseEntity<Optional<Stock>>(stockService.getStock(stockTicker), HttpStatus.OK);
    }

    @PutMapping("/{stockTicker}")
    public ResponseEntity<Stock> updateStock(@PathVariable String stockTicker, @RequestBody Stock updatedStock) {
        Stock updated = stockService.updateStock(stockTicker, updatedStock.getStockCurrPrice(), updatedStock.getStockDailyChange());
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // @Autowired
    // public StockController(StockService stockService) {
    // this.stockService = stockService;
    // }

    // @GetMapping
    // public List<Stock> getAllStocks() {
    // return stockService.getAllStocks();
    // }

    // @GetMapping("/{id}")
    // public ResponseEntity<Stock> getStockById(@PathVariable Long id) {
    // Stock stock = StockService.getStockById(id);
    // if (stock != null) {
    // return ResponseEntity.ok(stock);
    // } else {
    // return ResponseEntity.notFound().build();
    // }
    // }

    // @PostMapping
    // public ResponseEntity<Stock> createStock(@RequestBody Stock stock) {
    // Stock savedStock = stockService.saveStock(stock);
    // return ResponseEntity.status(HttpStatus.CREATED).body(savedStock);
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
    // stockService.deleteStock(id);
    // return ResponseEntity.noContent().build();
    // }
}
