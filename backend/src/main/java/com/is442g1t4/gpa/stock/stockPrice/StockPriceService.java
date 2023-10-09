package com.is442g1t4.gpa.stock.stockPrice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is442g1t4.gpa.stock.StockRepository;
import com.is442g1t4.gpa.stock.exceptions.StockNotFoundException;
import com.is442g1t4.gpa.stock.model.Stock;

import java.sql.Date;
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

    public List<StockPrice> getAllStockPrices() {
        return stockPriceRepository.findAll();
    }

    public List<StockPrice> getStockPriceByTicker(String stockTicker) {
        return stockPriceRepository.findStockPriceByStockTicker(stockTicker);
    }

    public StockPrice getStockPriceByDate(String stockTicker, Date date) {
        StockPrice stockPrice = stockPriceRepository.findStockPriceByStockTickerAndDate(stockTicker, date);
        return stockPrice;
    }

}
