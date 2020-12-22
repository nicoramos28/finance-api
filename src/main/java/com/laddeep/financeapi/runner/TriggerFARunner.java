package com.laddeep.financeapi.runner;

import com.laddeep.financeapi.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Slf4j
public class TriggerFARunner implements Runnable {

    private Monitor monitor;

    private StockService stockService;

    public TriggerFARunner(Monitor monitor, StockService stockService) {
        this.monitor = monitor;
        this.stockService = stockService;
    }

    @Override
    public void run() {
        for(String stockToProcess = monitor.receive();
            !"End".equals(stockToProcess); stockToProcess = monitor.receive()){
            System.out.println("Processing " + stockToProcess + "\n");
            stockService.geStockEmaValues(stockToProcess,null);
            stockService.geStockSmaValues(stockToProcess, null);
            stockService.getCandlesForStock(stockToProcess);
            try{
                Thread.sleep(ThreadLocalRandom.current().nextInt(4000,7000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.info("\n\n################  Thread interrupted  ################\n");
            }
        }


    }
}
