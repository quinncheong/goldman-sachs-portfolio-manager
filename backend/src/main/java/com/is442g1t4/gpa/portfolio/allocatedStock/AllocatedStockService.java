package com.is442g1t4.gpa.portfolio.allocatedStock;

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
public class AllocatedStockService {
    @Autowired
    private AllocatedStockRepository allocatedStockRepository;

    public AllocatedStock addAllocatedStock(AllocatedStock allocatedStock) {
        // AllocatedStock allcatedStock = allocatedStockRepository.findAllocatedStockByStockTicker(allocatedStock.getStockTicker());

        // if (allcatedStock == null){
            return allocatedStockRepository.save(allocatedStock);
        // }

        // return null;
    }

    public List<AllocatedStock> getAllAllocatedStocks() {
        return allocatedStockRepository.findAll();
    }

    public AllocatedStock getAllocatedStockById(String id) {
        return allocatedStockRepository.findAllocatedStockById(id);
    }

    public List<AllocatedStock> getAllocatedStockByStockTicker(String stockTicker) {
        return allocatedStockRepository.findAllocatedStockByStockTicker(stockTicker);
    }
    
}
