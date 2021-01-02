package com.laddeep.financeapi.runner;

import com.laddeep.financeapi.exceptions.ThreadException;
import com.laddeep.financeapi.service.StockAnalyticsService;
import com.laddeep.financeapi.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Slf4j
public class StockRunner implements Runnable {

    private Monitor monitor;

    private StockService stockService;

    private StockAnalyticsService analyticsService;

    public StockRunner(Monitor monitor, StockService stockService, StockAnalyticsService analyticsService) {
        this.monitor = monitor;
        this.stockService = stockService;
        this.analyticsService = analyticsService;
    }

    @Override
    public void run() {
        for(String stockToProcess = monitor.receive();
            !"End".equals(stockToProcess); stockToProcess = monitor.receive()){
                log.info("\n## ## ## ## ## ## ## ## Processing " + stockToProcess + " ## ## ## ## ## ## ## ##\n");
                stockService.geStockEmaSmaValues(stockToProcess);
                analyticsService.stockAnalyticsSignalAndConfirmation(stockToProcess);
            try{
                    Thread.sleep(ThreadLocalRandom.current().nextInt(4000,7000));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.info("\n\n################  Thread interrupted  ################\n");
                    try {
                        throw new ThreadException(e.getMessage());
                    } catch (IOException | InterruptedException ioException) {
                        ioException.printStackTrace();
                    }
                }
        }
    }
}
