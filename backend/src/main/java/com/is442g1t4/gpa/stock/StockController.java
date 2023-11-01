package com.is442g1t4.gpa.stock;

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

import com.is442g1t4.gpa.stock.model.Stock;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/stock")
public class StockController {
    @Autowired
    private StockService stockService;

    @GetMapping("/all")
    public ResponseEntity<List<Stock>> getAllStocks() {
        return new ResponseEntity<List<Stock>>(stockService.getAllStocks(),
                HttpStatus.OK);
    }

    @GetMapping("/{stockTicker}")
    public ResponseEntity<Optional<Stock>> getStock(@PathVariable String stockTicker) {
        return new ResponseEntity<Optional<Stock>>(stockService.getStockByTicker(stockTicker),
                HttpStatus.OK);
    }

    @GetMapping("/priceToday/{stockTicker}")
    public ResponseEntity<Double> getPriceTodayByTicker(@PathVariable String stockTicker) {
        return new ResponseEntity<Double>(stockService.getPriceTodayByTicker(stockTicker),
                HttpStatus.OK);
    }

    @GetMapping("/priceYesterday/{stockTicker}")
    public ResponseEntity<Double> getPriceYesterdayByTicker(@PathVariable String stockTicker) {
        return new ResponseEntity<Double>(stockService.getPriceYesterdayByTicker(stockTicker),
                HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> createStock(@RequestBody Stock stock) {
        Stock savedStock = stockService.addStock(stock);
        if (savedStock == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Stock already exists");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStock);
    }

    @PutMapping("/{stockTicker}")
    public ResponseEntity<Stock> updateStock(@RequestBody Stock stock){
        return new ResponseEntity<Stock>(stockService.updateStock(stock), HttpStatus.OK);
    }

}
