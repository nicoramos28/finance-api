package com.laddeep.financeapi.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Slf4j
public class SenderFARunner implements Runnable{

    private Monitor monitor;

    String[] watchList = {"CRM","FB","MSFT","BABA","MELI","AAPL","BA","NVDA","AMZN","SPY",
                                        "UAL","UBER","VIAC","VZ","WFC","WORK","X","XOM","TME","TSM",
                                        "TWTR","SNAP","SONO","SPCE","SPG","STWD","T","TCOM",
                                        "PAGS","PCG","PFE","PTON","RF","SAVE","SBUX","SLB",
                                        "MUR","NEM","NG","NIO","NKE","ON","ORCL","OXY","LUV","LYFT",
                                        "LYV","MGM","MPW","MS","MU","INTC","IQ","JBLU","JPM","KHC",
                                        "KMI","KO","LEVI","GM","GOLD","GPS","GRPN","GSX","HAL","HLT",
                                        "HPQ","DIS","DKNG","FCX","FEYE","GE","GILD","GIS","CHWY",
                                        "CMCSA","CNX","CSCO","CVS","CVX","DAL","DELL","BILI","BLMN",
                                        "BMY","BOX","BP","BSX","C","CCL","AAL","AMD","APA","ATVI",
                                        "BAC","BBBY","CNTG","QTT","LEN","DL","BLBD","AEY","TTC",
                                        "MLHR","ABM","NAV","SCHL","UXIN","APDN","RFIL","BB","WOR",
                                        "JBL","SCS","FDX","SAFM","RAD","ACN","CAMP","AIR","FUBO",
                                        "TC","APOG","MDY","VOO","DIA","QQQ","IWM","VTI","TLT",
                                        "GLD","FXB","IBB","TIP","AGG","IEF","SDY","FXE","OIH","FXY",
                                        "REZ","EFA","FXA","SVXY","XLU","VNQI","KRE","GDX","IRBO",
                                        "VXX","UVXY","SCHH","REET","FREL","KBWY","PSR","REK","O",
                                        "OHI","NRZ","CYBR","AXNX","MCD","RH","DHI","TGT","NLOK",
                                        "DLA","FIVN","HD","LOW","VNQ","XLRE","LGIH","KBH","OCC",
                                        "VNCE","HEI","CVGW","EPAC","FDS","ZNGA", "ZM", "M", "PLUG",
                                        "SQ", "KNDI", "BLNK", "MRVL", "SWN", "NOK", "SPXS",
                                        "TZA", "MRO", "SRNE", "NNDM", "F", "TEVA", "INO",
                                        "GNUS", "ITUB", "ET", "ACB", "NLY", "SNDL", "STOR"};

    //from SWN low cost. Exclude for short period in NYSE "MNSO",
    public SenderFARunner(Monitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        for (String stock : watchList) {
            monitor.send(stock);
            try{
                Thread.sleep(ThreadLocalRandom.current().nextInt(3000,5000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.info("\n\n################  Thread interrupted  ################\n");
            }
        }
    }
}