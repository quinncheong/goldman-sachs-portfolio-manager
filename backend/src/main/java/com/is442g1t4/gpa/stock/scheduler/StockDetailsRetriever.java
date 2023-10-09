package com.is442g1t4.gpa.stock.scheduler;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.is442g1t4.gpa.stock.model.Stock;

import reactor.core.publisher.Mono;

/**
 * ScheduledStockDetailsRetriever
 */
@Component
public class StockDetailsRetriever {

    private static String ALPHAVANTAGE_API_KEY;
    final WebClient baseQueryClient;

    public StockDetailsRetriever() {
        baseQueryClient = WebClient.builder().baseUrl("https://www.alphavantage.co/query").build();
    }

    @Value("${api.alphavantage.key}")
    public void setALPHAVANTAGE_API_KEY(String ALPHAVANTAGE_API_KEY) {
        StockDetailsRetriever.ALPHAVANTAGE_API_KEY = ALPHAVANTAGE_API_KEY;
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
