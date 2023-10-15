package com.is442g1t4.gpa.portfolio.allocatedStock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

// import org.modelmapper.ModelMapper;

import org.bson.types.ObjectId;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.text.SimpleDateFormat;

import com.is442g1t4.gpa.stock.StockService;
import com.is442g1t4.gpa.stock.model.Stock;

import io.micrometer.core.ipc.http.HttpSender.Response;

@RestController
@RequestMapping("/api/v1/allocated-stock")
public class AllocatedStockController {
    @Autowired
    private AllocatedStockService allocatedStockService;

    @PostMapping("/")
    public ResponseEntity<?> createAllocatedStock(@RequestBody AllocatedStock allocatedStock){
        AllocatedStock savedAllocatedStock = allocatedStockService.addAllocatedStock(allocatedStock);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAllocatedStock);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AllocatedStock>> getAllAllocatedStocks(){
        return new ResponseEntity<List<AllocatedStock>>(allocatedStockService.getAllAllocatedStocks(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AllocatedStock> getAllocatedStock(@PathVariable ObjectId id){
        if (allocatedStockService.getAllocatedStockById(id) == null) {
            return new ResponseEntity<AllocatedStock>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<AllocatedStock>(allocatedStockService.getAllocatedStockById(id), HttpStatus.OK);
    }

    @GetMapping("/stock/{stockTicker}")
    public ResponseEntity<List<AllocatedStock>> getAllocatedStockByStockTicker(@PathVariable String stockTicker){
        if (allocatedStockService.getAllocatedStockByStockTicker(stockTicker).isEmpty()) {
            return new ResponseEntity<List<AllocatedStock>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<AllocatedStock>>(allocatedStockService.getAllocatedStockByStockTicker(stockTicker), HttpStatus.OK);
    }

}
