package com.is442g1t4.gpa.stock.scheduler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.is442g1t4.gpa.stock.TrackedStockRepository;
import com.is442g1t4.gpa.stock.model.Stock;
import com.is442g1t4.gpa.stock.model.TrackedStock;

import reactor.core.publisher.Mono;

/**
 * ScheduledStockDetailsRetriever
 */
@Component
public class StockDetailsRetriever {

    private static String ALPHAVANTAGE_API_KEY;
    final WebClient baseQueryClient;

    @Autowired
    TrackedStockRepository trackedStockRepository;

    public StockDetailsRetriever() {
        baseQueryClient = WebClient.builder().baseUrl("https://www.alphavantage.co/query").build();
    }

    @Value("${api.alphavantage.key}")
    public void setALPHAVANTAGE_API_KEY(String ALPHAVANTAGE_API_KEY) {
        StockDetailsRetriever.ALPHAVANTAGE_API_KEY = ALPHAVANTAGE_API_KEY;
    }

    public List<Stock> retrieveAllStockDetails() {
        List<TrackedStock> trackedStocks = trackedStockRepository.findAll();
        List<Stock> stockDetails = new ArrayList<>();
        for (TrackedStock trackedStock : trackedStocks) {
            Stock completeStockDetails = retrieveOneStockDetails(trackedStock.getSymbol());
            stockDetails.add(completeStockDetails);

            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                // TODO Add to custom logger or DLQ
                e.printStackTrace();
            }
        }
        return stockDetails;
    }

    public Stock retrieveOneStockDetails(String stockSymbol) {
        Mono<ResponseEntity<Stock>> responseMono = baseQueryClient.get()
                .uri(builder -> builder
                        .queryParam("function", "OVERVIEW")
                        .queryParam("symbol", stockSymbol)
                        .queryParam("apikey", ALPHAVANTAGE_API_KEY)
                        .build())
                .retrieve()
                .toEntity(Stock.class);

        ResponseEntity<Stock> response = responseMono.block();
        System.out.println(response.getBody());
        return response.getBody();
    }
}
