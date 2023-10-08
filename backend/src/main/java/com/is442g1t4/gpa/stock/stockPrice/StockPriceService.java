package com.is442g1t4.gpa.stock.stockPrice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is442g1t4.gpa.stock.Stock;
import com.is442g1t4.gpa.stock.StockRepository;
import com.is442g1t4.gpa.stock.exceptions.StockNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class StockPriceService {
    @Autowired
    private StockPriceRepository stockPriceRepository;

    public StockPrice addStockPrice(StockPrice stockPrice) {

        StockPrice retrievedStockPrice = stockPriceRepository
                .findStockPriceByStockTickerAndDate(stockPrice.getStockTicker(), stockPrice.getDate());

        if (retrievedStockPrice == null) {
            return stockPriceRepository.save(stockPrice);
        }

        return null;

    }

}
