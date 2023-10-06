package com.is442g1t4.gpa.stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is442g1t4.gpa.stock.exception.StockNotFoundException;
import com.is442g1t4.gpa.stock.model.Stock;
import com.is442g1t4.gpa.stock.model.StockPrice;
import com.is442g1t4.gpa.stock.repository.StockPriceRepository;
import com.is442g1t4.gpa.stock.repository.StockRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StockPriceService {
    @Autowired
    private StockPriceRepository stockPriceRepository;

    public StockPrice addStockPrice(StockPrice stockPrice) {

        // get stock price by stock ticker and date
        // if there is already an existing ^, return null

        StockPrice retrievedStockPrice = stockPriceRepository.findStockPriceByStockTickerAndDate(stockPrice.getStockTicker(), stockPrice.getDate());

        if (retrievedStockPrice == null){
            return stockPriceRepository.save(stockPrice);
        }

        return null;

    }

}
