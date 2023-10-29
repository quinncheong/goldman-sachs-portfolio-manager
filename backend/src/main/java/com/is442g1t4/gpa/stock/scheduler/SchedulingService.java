package com.is442g1t4.gpa.stock.scheduler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is442g1t4.gpa.stock.StockRepository;
import com.is442g1t4.gpa.stock.TrackedStockRepository;
import com.is442g1t4.gpa.stock.model.Stock;
import com.is442g1t4.gpa.stock.model.TrackedStock;
import com.is442g1t4.gpa.stock.stockPrice.StockPrice;
import com.is442g1t4.gpa.stock.stockPrice.StockPriceRepository;

@Service
public class SchedulingService {

    @Autowired
    private TrackedStockRepository trackedStockRepository;

    @Autowired
    private StockPriceRepository stockPriceRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockDetailsRetriever stockDetailsRetriever;

    public void saveStockDetailsForAllStocks() {
        List<TrackedStock> trackedStocks = trackedStockRepository.findAll();

        for (TrackedStock trackedStock : trackedStocks) {
            Stock completedStockDetails = stockDetailsRetriever
                    .retrieveOneStockDetails(trackedStock.getSymbol());
            stockRepository.save(completedStockDetails);
            System.out.println("Saved for" + trackedStock.getSymbol());

            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                // TODO Add to custom logger or DLQ
                e.printStackTrace();
            }
        }

        return;
    }

    public void saveStockPricesForAllStocks() {
        List<TrackedStock> trackedStocks = trackedStockRepository.findAll();

        for (TrackedStock trackedStock : trackedStocks) {
            List<StockPrice> fullStockPrices = stockDetailsRetriever
                    .retrieveFullStockPrices(trackedStock.getSymbol());

            stockPriceRepository.saveAll(fullStockPrices);
            System.out.println("Saved for" + trackedStock.getSymbol());

            // try {
            // Thread.sleep(30000);
            // } catch (InterruptedException e) {
            // // TODO Add to custom logger or DLQ
            // e.printStackTrace();
            // }
        }
        return;
    }

    public void updateLatestStockPrices() {
        List<TrackedStock> trackedStocks = trackedStockRepository.findAll();

        for (TrackedStock trackedStock : trackedStocks) {
            List<StockPrice> latestStockPrices = stockDetailsRetriever
                    .retrieveCurrStockPrices(trackedStock.getSymbol());

            StockPrice priceToday = latestStockPrices.get(0);
            // StockPrice priceYesterday = latestStockPrices.get(1);

            StockPrice priceTodayFromDb = stockPriceRepository.findStockPriceByStockTickerAndDate(
                    priceToday.getStockTicker(), priceToday.getDate(),
                    priceToday.getDate());

            if (priceTodayFromDb == null) {
                stockPriceRepository.save(priceToday);
            }

            System.out.println("Saved for" + trackedStock.getSymbol());

            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                // TODO Add to custom logger or DLQ
                e.printStackTrace();
            }
        }
        return;
    }

}
