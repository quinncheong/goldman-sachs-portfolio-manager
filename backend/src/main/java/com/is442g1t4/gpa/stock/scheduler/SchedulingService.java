package com.is442g1t4.gpa.stock.scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            saveStockDetailsForOneStock(trackedStock.getSymbol());

            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                // TODO Add to custom logger or DLQ
                e.printStackTrace();
            }
        }

        return;
    }

    public void saveStockDetailsForOneStock(String symbol) {
        Stock completedStockDetails = stockDetailsRetriever
                .retrieveOneStockDetails(symbol);
        stockRepository.save(completedStockDetails);
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

    public void savePriceForOneStock(String stockSymbol) {
        List<StockPrice> fullStockPrices = stockDetailsRetriever.retrieveFullStockPrices(stockSymbol);
        stockPriceRepository.saveAll(fullStockPrices);
        System.out.println("Saved for stock: " + stockSymbol);
    }

    public void updateLatestStockPrices() {
        List<TrackedStock> trackedStocks = trackedStockRepository.findAll();

        for (TrackedStock trackedStock : trackedStocks) {
            updateLatestPriceForOneStock(trackedStock.getSymbol());

            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                // TODO Add to custom logger or DLQ
                e.printStackTrace();
            }
        }
        return;
    }

    public void updateLatestPriceForOneStock(String stockSymbol) {
        List<StockPrice> latestStockPrices = stockDetailsRetriever
                .retrieveCurrStockPrices(stockSymbol);

        StockPrice priceToday = latestStockPrices.get(0);
        StockPrice priceYesterday = latestStockPrices.get(1);

        System.out.print(stockSymbol);

        StockPrice priceTodayFromDb = stockPriceRepository.findStockPriceByStockTickerAndDate(
                priceToday.getStockTicker(),
                priceToday.getDate(),
                priceToday.getDate());

        if (priceTodayFromDb == null) {
            stockPriceRepository.save(priceToday);
        }

        Optional<Stock> stock = stockRepository.findStockBySymbol(stockSymbol);
        if (stock.isPresent()) {
            stock.get().setPriceToday(priceToday.getAdjustedClose());
            stock.get().setPriceYesterday(priceYesterday.getAdjustedClose());
            stockRepository.save(stock.get());
        }

        System.out.println("Saved for" + stockSymbol);

    }
}
