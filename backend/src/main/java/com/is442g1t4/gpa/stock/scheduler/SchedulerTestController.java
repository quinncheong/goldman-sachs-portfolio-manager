package com.is442g1t4.gpa.stock.scheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.is442g1t4.gpa.stock.model.Stock;
import com.is442g1t4.gpa.stock.stockPrice.StockPrice;
import com.is442g1t4.gpa.user.User;

@RestController
@RequestMapping("/test")
public class SchedulerTestController {
    @Autowired
    private StockDetailsRetriever stockDetailsRetriever;

    @GetMapping("/retrieve/all")
    public ResponseEntity<List<Stock>> getAllStocks() {
        System.out.println("hitting endpoint");
        List<Stock> stocks = stockDetailsRetriever.retrieveAllStockDetails();

        return new ResponseEntity<List<Stock>>(stocks, HttpStatus.OK);
    }

    @GetMapping("/retrieve/stock-price")
    public ResponseEntity<List<StockPrice>> getStockPrice() {

        List<StockPrice> prices = stockDetailsRetriever.retrieveStockPriceDetails("AAPL");
        return new ResponseEntity<List<StockPrice>>(prices, HttpStatus.OK);
    }
}
