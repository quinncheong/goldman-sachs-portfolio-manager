package com.is442g1t4.gpa.stock.scheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.is442g1t4.gpa.stock.TrackedStockRepository;
import com.is442g1t4.gpa.stock.model.Stock;
import com.is442g1t4.gpa.stock.model.TrackedStock;
import com.is442g1t4.gpa.stock.stockPrice.StockPrice;
import com.is442g1t4.gpa.stock.stockPrice.StockPriceRepository;
import com.is442g1t4.gpa.utility.DateParser;

import com.is442g1t4.gpa.stock.scheduler.AlphavantageResponse.AlphavantageStockPrice;

import reactor.core.publisher.Mono;

/**
 * ScheduledStockDetailsRetriever
 */
@Component
public class StockDetailsRetriever {

    private final int MAX_BUFFER_SIZE = 16 * 1024 * 1024;
    private static String ALPHAVANTAGE_API_KEY;
    final WebClient baseQueryClient;

    @Autowired
    private TrackedStockRepository trackedStockRepository;

    public StockDetailsRetriever() {
        baseQueryClient = WebClient.builder()
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer
                                .defaultCodecs()
                                .maxInMemorySize(MAX_BUFFER_SIZE))
                        .build())
                .baseUrl("https://www.alphavantage.co/query")
                .build();
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
        return response.getBody();
    }

    // https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=IBM&outputsize=full&apikey=demo
    public List<StockPrice> retrieveFullStockPrices(String stockSymbol) {
        Mono<ResponseEntity<AlphavantageResponse>> responseMono = baseQueryClient.get()
                .uri(builder -> builder
                        .queryParam("function", "TIME_SERIES_DAILY_ADJUSTED")
                        .queryParam("symbol", stockSymbol)
                        .queryParam("outputsize", "full")
                        .queryParam("apikey", ALPHAVANTAGE_API_KEY)
                        .build())
                .retrieve()
                .toEntity(AlphavantageResponse.class);

        ResponseEntity<AlphavantageResponse> response = responseMono.block();
        Map<String, AlphavantageStockPrice> dailyTimeSeries = response.getBody().getDailyTimeSeries();

        ArrayList<StockPrice> stockPrices = new ArrayList<>();
        for (String date : dailyTimeSeries.keySet()) {

            AlphavantageStockPrice currStockPrice = dailyTimeSeries.get(date);
            StockPrice stockPrice = new StockPrice(
                    stockSymbol,
                    DateParser.parseDateString(date, "yyyy-MM-dd"),
                    currStockPrice.getOpen(),
                    currStockPrice.getHigh(),
                    currStockPrice.getClose(),
                    currStockPrice.getLow(),
                    currStockPrice.getAdjustedClose());
            stockPrices.add(stockPrice);
        }

        return stockPrices;

    }

    public List<StockPrice> retrieveCurrStockPrices(String stockSymbol) {
        Mono<ResponseEntity<AlphavantageResponse>> responseMono = baseQueryClient.get()
                .uri(builder -> builder
                        .queryParam("function", "TIME_SERIES_DAILY_ADJUSTED")
                        .queryParam("symbol", stockSymbol)
                        .queryParam("outputsize", "compact")
                        .queryParam("apikey", ALPHAVANTAGE_API_KEY)
                        .build())
                .retrieve()
                .toEntity(AlphavantageResponse.class);

        ResponseEntity<AlphavantageResponse> response = responseMono.block();
        Map<String, AlphavantageStockPrice> dailyTimeSeries = response.getBody().getDailyTimeSeries();

        ArrayList<StockPrice> stockPrices = new ArrayList<>();
        for (String date : dailyTimeSeries.keySet()) {

            AlphavantageStockPrice currStockPrice = dailyTimeSeries.get(date);
            StockPrice stockPrice = new StockPrice(
                    stockSymbol,
                    DateParser.parseDateString(date, "yyyy-MM-dd"),
                    currStockPrice.getOpen(),
                    currStockPrice.getHigh(),
                    currStockPrice.getClose(),
                    currStockPrice.getLow(),
                    currStockPrice.getAdjustedClose());
            stockPrices.add(stockPrice);
        }

        return stockPrices;
    }
}
