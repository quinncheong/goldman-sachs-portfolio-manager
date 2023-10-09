package com.is442g1t4.gpa.stock.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.is442g1t4.gpa.stock.StockRepository;
import com.is442g1t4.gpa.stock.model.Stock;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Component
public class ScheduledTasks {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Autowired
    StockDetailsRetriever stockDetailsRetriever;

    @Autowired
    StockRepository stockRepository;

    // @Scheduled(cron = "0 22 20 * * MON-FRI")
    // public void repopulateStockDetailsDaily() {
    // System.out.println("Running CRON at 20:22" +
    // dateTimeFormatter.format(LocalDateTime.now()));
    // ArrayList<Stock> stockDetails =
    // stockDetailsRetriever.retrieveOneStockDetails("AAPL");
    // stockRepository.saveAll(stockDetails);
    // }

}