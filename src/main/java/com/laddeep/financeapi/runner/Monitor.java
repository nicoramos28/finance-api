package com.laddeep.financeapi.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Monitor {

    private String ticker;

    private boolean processed = true;

    public synchronized void send(String ticker){
        while(!processed){
            try{
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.info("\n\n################  Thread interrupted  ################\n");
            }
        }
        processed = false;
        this.ticker = ticker;
        notifyAll();
    }

    public synchronized String receive(){
        while(processed){
            try{
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.info("\n\n################  Thread interrupted  ################\n");
            }
        }
        processed = true;
        notifyAll();
        return this.ticker;
    }
}
