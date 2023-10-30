package com.is442g1t4.gpa.stock.scheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    private SchedulingService schedulingService;

    @GetMapping("/retrieve/all")
    public ResponseEntity<List<Stock>> getAllStocks() {
        List<Stock> stocks = stockDetailsRetriever.retrieveAllStockDetails();

        return new ResponseEntity<List<Stock>>(stocks, HttpStatus.OK);
    }

    @GetMapping("/retrieve/stock-price")
    public ResponseEntity<List<StockPrice>> getAllStockPrice() {
        schedulingService.saveStockPricesForAllStocks();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/retrieve/stock-price/latest")
    public ResponseEntity<List<StockPrice>> updateLatestStockPrice() {
        schedulingService.updateLatestStockPrices();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/retrieve/one-stock-price/{stockSymbol}")
    public ResponseEntity<List<StockPrice>> getOneStockPrice(@PathVariable String stockSymbol) {
        String[] dowSymbols = new String[] {
                // "AAPL",
                // "MSFT",
                // "GOOGL",
                // "AMZN",
                // "META",
                // "JPM",
                // "V",
                // "JNJ",
                // "WMT",
                // "PG",
                // "UNH",
                // "HD",
                // "T",
                // "PYPL",
                // "VZ",
                // "CSCO",
                // "XOM",
                // "CVX",
                // "INTC",
                // "KO",
                // "MRK",
                // "PEP",
                // "BAC",
                // "CMCSA",
                // "DIS",
                // "IBM",
                // "ORCL",
                // "GS",
                // "NKE",
                // "CRM"
        };

        for (String stock : dowSymbols) {
            schedulingService.savePriceForOneStock(stock);
        }

        // List<StockPrice> prices =
        // stockDetailsRetriever.retrieveFullStockPrices(stockSymbol);
        return new ResponseEntity<List<StockPrice>>(HttpStatus.OK);
    }

}
