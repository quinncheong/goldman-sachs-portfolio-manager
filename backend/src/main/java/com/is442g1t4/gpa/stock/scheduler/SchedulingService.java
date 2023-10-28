package com.is442g1t4.gpa.stock.scheduler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private StockDetailsRetriever stockDetailsRetriever;

    public void saveStockPricesForAllStocks() {
        List<TrackedStock> trackedStocks = trackedStockRepository.findAll();

        for (TrackedStock trackedStock : trackedStocks) {
            List<StockPrice> fullStockPrices = stockDetailsRetriever
                    .retrieveOneStockPriceDetails(trackedStock.getSymbol());

            stockPriceRepository.saveAll(fullStockPrices);
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
