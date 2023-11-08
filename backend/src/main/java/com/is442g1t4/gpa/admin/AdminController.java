package com.is442g1t4.gpa.admin;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.is442g1t4.gpa.stock.model.TrackedStock;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/seed/tracked-stock")
    public ResponseEntity<?> seedTrackedStocks() {
        return new ResponseEntity<List<TrackedStock>>(adminService.seedTrackedStocks(),
                HttpStatus.OK);
    }

    @GetMapping("/seed/stock-price")
    public ResponseEntity<?> seedStockPrice() {
        return new ResponseEntity<List<TrackedStock>>(adminService.seedTrackedStocks(),
                HttpStatus.OK);
    }

}
