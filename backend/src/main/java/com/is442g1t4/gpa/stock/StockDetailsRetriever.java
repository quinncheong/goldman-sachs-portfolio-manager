package com.is442g1t4.gpa.stock;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

/**
 * ScheduledStockDetailsRetriever
 */
@Component
public class StockDetailsRetriever {

    final WebClient baseQueryClient;

    public StockDetailsRetriever() {
        baseQueryClient = WebClient.builder().baseUrl("https://www.alphavantage.co/query").build();
    }

    public StockDetailsResponse retrieveStockDetails() {
        Mono<ResponseEntity<StockDetailsResponse>> responseMono = baseQueryClient.get()
                .uri(builder -> builder
                        .queryParam("function", "OVERVIEW")
                        .queryParam("symbol", "IBM")
                        .queryParam("apikey", "demo")
                        .build())
                .retrieve()
                .toEntity(StockDetailsResponse.class);

        ResponseEntity<StockDetailsResponse> response = responseMono.block();
        System.out.println(response.getBody());
        return response.getBody();
    }

    // public String retrieve() {

    // String uri =
    // "https://www.alphavantage.co/query?function=OVERVIEW&symbol=IBM&apikey=demo";
    // WebClient.Builder webClientBuilder = WebClient.builder();

    // String res = webClientBuilder.build()
    // .get()
    // .uri(uri)
    // .retrieve()
    // .bodyToMono(String.class).block();

    // System.out.println(res);
    // return res;
    // }

}
