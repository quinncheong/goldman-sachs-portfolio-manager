package com.is442g1t4.gpa.portfolio.allocatedStock;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is442g1t4.gpa.stock.StockRepository;
import com.is442g1t4.gpa.stock.model.Stock;
import com.is442g1t4.gpa.portfolio.allocatedStock.AllocatedStockService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AllocatedStockService {
    @Autowired
    private AllocatedStockRepository allocatedStockRepository;

    @Autowired
    private StockRepository stockRepository;

    public AllocatedStock addAllocatedStock(String symbol, int quantity) {
        Optional<Stock> target = stockRepository.findStockBySymbol(symbol);
        if (!target.isPresent()) {

            return null;
        } else {
            ObjectId id = new ObjectId();
            AllocatedStock allocatedStock = new AllocatedStock(id, symbol, quantity, target.get().getPriceToday(),
                    new Date());

            return allocatedStockRepository.save(allocatedStock);
        }
    }

    public AllocatedStock addAllocatedStock(AllocatedStock allocatedStock) {

        return allocatedStockRepository.save(allocatedStock);
    }

    public List<AllocatedStock> getAllAllocatedStocks() {
        return allocatedStockRepository.findAll();
    }

    public AllocatedStock getAllocatedStockById(ObjectId id) {
        return allocatedStockRepository.findAllocatedStockById(id);
    }

    public List<AllocatedStock> getAllocatedStockByStockTicker(String stockTicker) {
        return allocatedStockRepository.findAllocatedStockByStockTicker(stockTicker);
    }

}
