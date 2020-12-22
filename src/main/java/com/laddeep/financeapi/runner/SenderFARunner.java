package com.laddeep.financeapi.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Slf4j
public class SenderFARunner implements Runnable{

    private Monitor monitor;

    private final String[] watchList = {"MDY", "SPY", "VOO", "DIA", "QQQ", "IWM", "VTI", "TLT", "GLD", "FXB",
            "IBB", "TIP", "AGG", "IEF", "SDY", "FXE", "OIH", "FXY", "REZ", "EFA", "FXA", "SVXY", "XLU",
            "VNQI", "KRE", "GDX", "IRBO", "VXX", "UVXY", "SCHH", "REET", "FREL", "KBWY", "PSR", "REK",
            "O", "OHI", "NRZ", "GE", "CYBR", "AXNX", "MCD", "LEN", "RH", "DHI", "TGT", "NLOK", "FDX",
            "DLA", "FIVN", "HD", "LOW", "VNQ", "XLRE", "LGIH", "KBH"};

    public SenderFARunner(Monitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        for (String stock : watchList) {
            monitor.send(stock);
            try{
                Thread.sleep(ThreadLocalRandom.current().nextInt(4000,6000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.info("\n\n################  Thread interrupted  ################\n");
            }
        }
    }
}