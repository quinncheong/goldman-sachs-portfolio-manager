package com.is442g1t4.gpa.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is442g1t4.gpa.stock.StockRepository;
import com.is442g1t4.gpa.stock.TrackedStockRepository;
import com.is442g1t4.gpa.stock.model.Stock;
import com.is442g1t4.gpa.stock.model.TrackedStock;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private TrackedStockRepository trackedStockRepository;

    @Autowired
    private StockRepository stockRepository;

    public List<TrackedStock> seedTrackedStocks() {
        // Drop collection and then repopulate with only ticker from
        trackedStockRepository.deleteAll();
        ArrayList<String> DJSymbols = new ArrayList<>(
                Arrays.asList("AAPL",
                        "MSFT",
                        "GOOGL",
                        "AMZN",
                        "FB",
                        "JPM",
                        "V",
                        "JNJ",
                        "WMT",
                        "PG",
                        "UNH",
                        "HD",
                        "T",
                        "PYPL",
                        "VZ",
                        "CSCO",
                        "XOM",
                        "CVX",
                        "INTC",
                        "KO",
                        "MRK",
                        "PEP",
                        "BAC",
                        "CMCSA",
                        "DIS",
                        "IBM",
                        "ORCL",
                        "GS",
                        "NKE",
                        "CRM"));

        ArrayList<TrackedStock> trackedStocks = new ArrayList<>();
        for (String ticker : DJSymbols) {
            trackedStocks.add(new TrackedStock(ticker));
        }
        trackedStockRepository.deleteAll();
        return trackedStockRepository.saveAll(trackedStocks);
    }

    public void seedStockPrice() {

    }
}
