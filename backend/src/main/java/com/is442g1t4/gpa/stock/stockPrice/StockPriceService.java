package com.is442g1t4.gpa.stock.stockPrice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StockPriceService {
    @Autowired
    private StockPriceRepository stockPriceRepository;

    public StockPrice addStockPrice(StockPrice stockPrice) {
        Date nextDay = new Date(stockPrice.getDate().getTime() + 86400000);
        StockPrice retrievedStockPrice = stockPriceRepository.findStockPriceByStockTickerAndDate(stockPrice.getStockTicker(), stockPrice.getDate(), nextDay);

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

    public StockPrice getStockPriceBySymbolAndDate(String stockTicker, Date date) {
        Date nextDay = new Date(date.getTime() + 86400000);
        StockPrice stockPrice = stockPriceRepository.findStockPriceByStockTickerAndDate(stockTicker, date, nextDay);
        return stockPrice;
    }

}
