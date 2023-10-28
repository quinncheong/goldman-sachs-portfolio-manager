package com.is442g1t4.gpa.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is442g1t4.gpa.stock.model.Stock;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Optional<Stock> getStockByTicker(String stockSymbol) {
        return stockRepository.findStockBySymbol(stockSymbol);
    }

    public Stock updateStock(Stock stock) {
        return stockRepository.save(stock);
    }

    public void deleteStock() {
    }

    public Stock addStock(Stock stock) {
        Optional<Stock> retrievedStock = getStockByTicker(stock.getSymbol());
        if (retrievedStock.isPresent()) {
            return null;
        }
        return stockRepository.save(stock);
    }

    public double getPriceTodayByTicker(String stockSymbol) {
        Optional<Stock> retrievedStock = getStockByTicker(stockSymbol);
        if (retrievedStock.isPresent()) {
            return retrievedStock.get().getPriceToday() != null ? : 50.0;
            return 50.0; // hardcoding for now
        }
        return 0;
    }

    public double getPriceYesterdayByTicker(String stockSymbol) {
        Optional<Stock> retrievedStock = getStockByTicker(stockSymbol);
        if (retrievedStock.isPresent()) {
            return 40.0; // hardcoding for now
            // return retrievedStock.get().getPriceYesterday();
        }
        return 0;
    }

}
