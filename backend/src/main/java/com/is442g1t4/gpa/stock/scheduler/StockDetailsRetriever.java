package com.is442g1t4.gpa.stock.scheduler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

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

    public StockDetailsResponse retrieveStockDetails(String stockTicker) {

        Mono<ResponseEntity<StockDetailsResponse>> responseMono = baseQueryClient.get()
                .uri(builder -> builder
                        .queryParam("function", "OVERVIEW")
                        .queryParam("symbol", stockTicker)
                        .queryParam("apikey", ALPHAVANTAGE_API_KEY)
                        .build())
                .retrieve()
                .toEntity(StockDetailsResponse.class);

        ResponseEntity<StockDetailsResponse> response = responseMono.block();
        System.out.println(response.getBody());
        return response.getBody();
    }

}
