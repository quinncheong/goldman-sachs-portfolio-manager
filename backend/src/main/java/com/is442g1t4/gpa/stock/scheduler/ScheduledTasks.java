package com.is442g1t4.gpa.stock.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.is442g1t4.gpa.stock.StockRepository;
import com.is442g1t4.gpa.stock.model.Stock;
import com.is442g1t4.gpa.stock.stockPrice.StockPrice;
import com.is442g1t4.gpa.stock.stockPrice.StockPriceRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.util.List;

@Component
public class ScheduledTasks {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Autowired
    StockDetailsRetriever stockDetailsRetriever;

    @Autowired
    StockRepository stockRepository;

    @Autowired
    StockPriceRepository stockPriceRepository;

    @Scheduled(cron = "0 00 05 * * MON-FRI")
    public void repopulateStockDetailsDaily() {
        LocalDateTime start = LocalDateTime.now();
        System.out.println("Running Daily repopulate stock details CRON Job" + dateTimeFormatter.format(start));

        List<Stock> stockDetails = stockDetailsRetriever.retrieveAllStockDetails();
        stockRepository.saveAll(stockDetails);

        LocalDateTime end = LocalDateTime.now();
        System.out.println("Completed Daily repopulate stock details CRON Job" + dateTimeFormatter.format(end));

        Duration duration = Duration.between(start, end);
        System.out.println("Total duration: " + duration.toSeconds() + " seconds");
    }

    // @Scheduled(cron = "0 30 20 * * MON-FRI")
    @Scheduled(cron = "03 56 20 * * MON-SUN")
    public void repopulateStockPriceDaily() {
        LocalDateTime start = LocalDateTime.now();
        System.out.println("Running Daily repopulate stock details CRON Job" + dateTimeFormatter.format(start));

        List<StockPrice> stockDetails = stockDetailsRetriever.retrieveStockPriceDetails("AAPL");
        stockPriceRepository.saveAll(stockDetails);

        LocalDateTime end = LocalDateTime.now();
        System.out.println("Completed Daily repopulate stock details CRON Job" + dateTimeFormatter.format(end));

        Duration duration = Duration.between(start, end);
        System.out.println("Total duration: " + duration.toSeconds() + " seconds");
    }

}