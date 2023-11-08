package com.is442g1t4.gpa.stock.scheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.is442g1t4.gpa.stock.model.Stock;
import com.is442g1t4.gpa.stock.stockPrice.StockPrice;

@RestController
@RequestMapping("/test")
public class SchedulerController {
    @Autowired
    private StockDetailsRetriever stockDetailsRetriever;

    @Autowired
    private SchedulingService schedulingService;

    @GetMapping("/retrieve/all")
    public ResponseEntity<List<Stock>> getAllStocks() {
        List<Stock> stocks = stockDetailsRetriever.retrieveAllStockDetails();

        return new ResponseEntity<List<Stock>>(stocks, HttpStatus.OK);
    }

    @GetMapping("/retrieve/stock-details/{symbol}")
    public ResponseEntity<Stock> getAllStocks(@PathVariable String symbol) {
        schedulingService.saveStockDetailsForOneStock(symbol);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/retrieve/stock-price")
    public ResponseEntity<List<StockPrice>> getAllStockPrice() {
        schedulingService.saveStockPricesForAllStocks();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/retrieve/stock-price/{symbol}")
    public ResponseEntity<List<StockPrice>> getOneStockPrice(@PathVariable String symbol) {
        String[] dowSymbols = new String[] {
        };

        for (String stock : dowSymbols) {
            schedulingService.savePriceForOneStock(stock);
        }

        return new ResponseEntity<List<StockPrice>>(HttpStatus.OK);
    }

    @GetMapping("/retrieve/stock-price/latest")
    public ResponseEntity<List<StockPrice>> updateLatestStockPrice() {
        schedulingService.updateLatestStockPrices();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/retrieve/stock-price/latest/{symbol}")
    public ResponseEntity<List<StockPrice>> updateLatestStockPriceForOne(@PathVariable String symbol) {
        schedulingService.updateLatestPriceForOneStock(symbol);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
