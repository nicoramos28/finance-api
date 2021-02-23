package com.laddeep.financeapi.service;

import com.laddeep.financeapi.entity.api.EarningDTO;
import com.laddeep.financeapi.entity.api.StockPriceDTO;
import com.laddeep.financeapi.entity.db.StockEma;
import com.laddeep.financeapi.entity.db.StockPrice;
import com.laddeep.financeapi.entity.db.StockSma;
import com.laddeep.financeapi.integrations.telegram.TelegramClient;
import com.laddeep.financeapi.util.CandleUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
public class TelegramMessageService {

    private TelegramClient telegramClient;

    private CandleUtil candleUtil;

    public TelegramMessageService(
            TelegramClient telegramClient,
            CandleUtil candleUtil
    ){
        this.telegramClient = telegramClient;
        this.candleUtil = candleUtil;
    }

    public void notifyStockPriceQuote(String quote, StockPriceDTO quoteData) throws IOException, InterruptedException {
        String message = quote + " prices :\n"
                + "----- Current price : " + quoteData.getCurrentPrice() + "\n"
                + "----- Open price : " + quoteData.getOpenPrice() + "\n"
                + "----- Highest price : " + quoteData.getHighestPrice() + "\n"
                + "----- Lowest price : " + quoteData.getLowestPrice() + "\n"
                + "----- Previous close price : " + quoteData.getPreviousClosePrice() + "";
        telegramClient.sendMessage(message);
    }

    public void notifyThreadException(String exception) throws IOException, InterruptedException {
        String message = "There is a problem in Finance API, the thread was interrupted by : " + exception;
        telegramClient.sendMessage(message);
    }

    public void notifySignalConfirmationStrategy(String ticker, StockPrice c1, StockPrice c2, StockEma e7, StockEma e30, StockSma s200) throws IOException, InterruptedException {
        String trend = (s200.getStatus() == -1)? "under" : "above";
        String volumen = (c1.getVolumen() < 50000)? " is under 50.000" : "is " + c1.getVolumen();
        String maStatus = null;
        String currentOrClosePrice = null;
        String position = null;
        switch (e7.getStatus()){
            case -1 : maStatus = "under";
                break;
            case 1 : maStatus = "above";
                break;
            case 0 : maStatus = "equal";
                break;
        }
        if(c1.getCurrentPrice().compareTo(BigDecimal.ZERO) == 0){
            currentOrClosePrice = "---- Close price : " + c1.getClosePrice() + "\n";
            if(candleUtil.getCandleType(c1) == 1){
                position = (c2.getHighestPrice().compareTo(c1.getClosePrice()) == 1)? "failed to close above the penultimate candle." : "closed above the penultimate candle.";
            }else{
                position = (c2.getHighestPrice().compareTo(c1.getClosePrice()) == 1)? "failed to close under the penultimate candle." : "closed under the penultimate candle.";
            }
        }else {
            currentOrClosePrice = "---- Current price : " + c1.getCurrentPrice() + "\n";
            if(candleUtil.getCandleType(c1) == 1){
                position = (c2.getHighestPrice().compareTo(c1.getCurrentPrice()) == 1)? "failed to close above the penultimate candle." : "closed above the penultimate candle.";
            }else{
                position = (c2.getLowestPrice().compareTo(c1.getCurrentPrice()) == 1)?  "closed under the penultimate candle." : "failed to close under the penultimate candle.";
            }
        }
        String message = "######### Confirmation to : " + ticker + " #########\n" +
                ticker + " actual prices : \n" +
                currentOrClosePrice +
                "---- Open price : " + c1.getOpenPrice() + "\n" +
                "---- Highest price : " + c1.getHighestPrice() + "\n" +
                "---- Lowest price : " + c1.getLowestPrice() + "\n" +
                "Movining Average values :\n" +
                "---- EMA 7 value : " + e7.getValue() + "\n" +
                "---- EMA 30 value : " + e30.getValue() + "\n" +
                "---- SMA 200 value : " + s200.getValue() + "\n" +
                "---- Actual volume " + volumen + "\n" +
                "The last candle " + position + "\n" +
                "Exponential Moving Averages are " + maStatus + " the price and Simple MovingAverage are " +
                trend + " the price.\n * ---------- ---------- ---------- * ---------- ---------- ---------- *";
        telegramClient.sendMessage(message);
    }

    public void notifyDailyEarning(List<EarningDTO> earnings) throws IOException, InterruptedException {
        String message = "######### EARNING TO NEXT NYSE SESSION #########";
        for (EarningDTO earning : earnings) {
            message += "\n******** " + earning.getSymbol() + " ********\n" +
                    "---- Current Stock Price : " + earning.getCurrentPrice() + "\n" +
                    "---- EPS estimate : " + earning.getEpsEstimate() + "\n" +
                    "---- Revenue Estimate : " + earning.getRevenueEstimate() + "\n" +
                    "---- Announcement : " + earning.getHour();
        }
        message += "\n* ---------- ---------- ---------- * ---------- ---------- ---------- *";
        telegramClient.sendMessage(message);
    }

    public void notifyTwoCandles(List<String> tickers) throws IOException, InterruptedException {
        final String[] message = {"---- Notification of two candles \n"};
        tickers.forEach(ticker->{
            message[0] += " " + ticker + "\n";
                });
        telegramClient.sendMessage(message[0]);
    }
}
