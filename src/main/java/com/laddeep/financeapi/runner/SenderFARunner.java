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


    String[] watchList2 = {"ABM","ABT","ACM","ADI","AGCO","ALB","AMAT","AMED","AMG","ARW","ATKR",
            "ATR","AVT","AZPN","BC","BGFV","BIG","BRKS","BWA","CAH","CALX","CBT",
            "CCK","CDK","CDNS","CE","CHE","COLM","COO","COUP","CRM","CRWD","D",
            "DD","DGX","DT","EBAY","EMN","ENR","ENTG","EPAM","ETN","FIVN","FN",
            "FOXF","FTNT","FTV","GDOT","GLW","GRMN","HEAR","HI","HIBB","HOLX","HRC",
            "HUBS","HZO","IDXX","INTC","INTU","IPAR","IRBT","JBL","KEYS","KFRC",
            "KLAC","KLIC","KNX","KR","KTB","LAMR","LB","LH","LMAT","LRCX","LSTR","MASI",
            "MATX","MHK","MKSI","MTD","MTOR","MU","NFLX","OMCL","ONTO","PANW",
            "PFPT","PH","PII","PKI","PLT","PLXS","PNR","PTC","QCOM","QRVO","RGEN",
            "SCVL","SGH","SLAB","SLGN","SNBR","SNX","SSTK","STMP","SWKS","SYKE","TDY",
            "TEL","TENB","TER","TNC","TPX","TXN","USNA","VSTO","WHR","WST","XPO",
            "ZM","ZS","GTLS" ,"CTRN","RS" ,"MTRN","AMAT","COLD","GLOB"
    };
    //remove from list "JWA","NVMI", "SAM"


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