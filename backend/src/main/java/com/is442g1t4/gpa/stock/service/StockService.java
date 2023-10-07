package com.is442g1t4.gpa.stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is442g1t4.gpa.stock.exception.StockNotFoundException;
import com.is442g1t4.gpa.stock.model.Stock;
import com.is442g1t4.gpa.stock.repository.StockRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Optional<Stock> getStockByTicker(String stockTicker) {
        return stockRepository.findStockByStockTicker(stockTicker);
    }

    public Stock updateStock(Stock stock) {
        return stockRepository.save(stock);
    }

    public void deleteStock() {
    }

    public Stock addStock(Stock stock) {
        Optional<Stock> retrievedStock = getStockByTicker(stock.getStockTicker());
        if (retrievedStock.isPresent()){
            return null;
        }
        return stockRepository.save(stock);
    }


}
