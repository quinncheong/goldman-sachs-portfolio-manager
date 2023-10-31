package com.is442g1t4.gpa.stock.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

@Component
public class ScheduledTasks {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Autowired
    SchedulingService schedulingService;

    // @Scheduled(cron = "0 00 05 * * MON-FRI")
    public void repopulateStockDetailsDaily() {
        LocalDateTime start = LocalDateTime.now();
        System.out.println("Running Daily repopulate stock details CRON Job" + dateTimeFormatter.format(start));

        schedulingService.saveStockDetailsForAllStocks();

        LocalDateTime end = LocalDateTime.now();
        System.out.println("Completed Daily repopulate stock details CRON Job" + dateTimeFormatter.format(end));

        Duration duration = Duration.between(start, end);
        System.out.println("Total duration: " + duration.toSeconds() + " seconds");
    }

    @Scheduled(cron = "0 00 08 * * MON-FRI")
    public void repopulateStockPriceDaily() {
        LocalDateTime start = LocalDateTime.now();
        System.out.println("Running daily Update Stock Price CRON Job" + dateTimeFormatter.format(start));

        schedulingService.updateLatestStockPrices();

        LocalDateTime end = LocalDateTime.now();
        System.out.println("Completed Daily Update Stock Price CRON Job" + dateTimeFormatter.format(end));

        Duration duration = Duration.between(start, end);
        System.out.println("Total duration: " + duration.toSeconds() + " seconds");
    }

}