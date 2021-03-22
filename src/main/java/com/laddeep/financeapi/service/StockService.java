package com.laddeep.financeapi.service;

import com.laddeep.financeapi.entity.api.*;
import com.laddeep.financeapi.component.StockBean;
import com.laddeep.financeapi.entity.db.Quote;
import com.laddeep.financeapi.entity.db.StockPrice;
import com.laddeep.financeapi.exceptions.BadRequestException;
import com.laddeep.financeapi.mapper.StockPriceDTOMapper;
import com.laddeep.financeapi.integrations.finnhub.FinnhubClient;
import com.laddeep.financeapi.integrations.finnhub.api.StockPriceQuote;
import com.laddeep.financeapi.util.DateUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.jvnet.hk2.annotations.Service;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
public class StockService{

    private QuoteService quoteService;

    private StockPriceDTOMapper stockPriceMapper;

    private FinnhubClient finnhubClient;

    private StockBean stockBean;

    private TelegramMessageService messageService;

    private DateUtil dateUtil;


    public StockService(
            QuoteService quoteService,
            StockPriceDTOMapper quoteDtoMapper,
            FinnhubClient finnhubClient,
            StockBean stockBean,
            TelegramMessageService messageService,
            DateUtil dateUtil
    ) {
        this.quoteService = quoteService;
        this.stockPriceMapper = quoteDtoMapper;
        this.finnhubClient = finnhubClient;
        this.stockBean = stockBean;
        this.messageService = messageService;
        this.dateUtil = dateUtil;
    }

    public StockPriceDTO getStockPrice(Quote quote){
        StockPriceQuote response = this.finnhubClient.getStockPriceQuote(quote.getQuote());
        if(response != null){
            if(response.getC().compareTo(BigDecimal.ZERO) != 0){
                stockBean.saveStockPrice(quote, response);
                return stockPriceMapper.map(response);
            }else{
                try {
                    messageService.notifyThreadException("ticker "+ quote.getQuote() +" could not be found");
                } catch (IOException | InterruptedException e) {
                    throw new BadRequestException("Could not sent telegram message alarm. Cause : " + "Ticker could not be found.");
                }
                return null;
            }
        }else{
            throw new BadRequestException("Invalid Request");
        }
    }

    public Long saveStockToFollow(Quote quote){
        return stockBean.saveStockToFollow(quote);
    }

    public void geStockEmaSmaValues(String ticker){
        Quote quote = stockBean.get(ticker);
        //Getting current stock price
        EmasDTO ema7 = this.finnhubClient.getMovingAverage(quote.getQuote(), OffsetDateTime.now(), EmasDTO.class, 7);
        EmasDTO ema30 = this.finnhubClient.getMovingAverage(quote.getQuote(), OffsetDateTime.now(), EmasDTO.class, 30);
        SmasDTO sma200 = this.finnhubClient.getMovingAverage(quote.getQuote(), OffsetDateTime.now(), SmasDTO.class, 200);
        EmaDTO e7 = null;
        EmaDTO e30 = null;
        SmaDTO s200 = null;
        OffsetDateTime day = OffsetDateTime.now();
        int i = 1;
        if(ema7 != null){
            while(i < 4){
                while(dateUtil.isWeekend(day) || stockBean.isHolidays(day)){
                    if(dateUtil.isSaturday(day) || stockBean.isHolidays(day)) {
                        day = day.minus(1, ChronoUnit.DAYS);
                    }else if(dateUtil.isSunday(day)){
                        day = day.minus(2, ChronoUnit.DAYS);
                    }
                }
                e7 = ema7.retreiveEma(ema7, i);
                e30 = ema30.retreiveEma(ema30, i);
                s200 = sma200.retreiveSma(sma200, i);
                BigDecimal currentPrice = BigDecimal.ZERO;
                BigDecimal previousClose = BigDecimal.ZERO;
                BigDecimal closePrice = BigDecimal.ZERO;

                //Updating or Create new Candle information
                StockPrice stockToSave = new StockPrice();
                stockToSave.setQuoteId(quote.getId());
                stockToSave.setTime(day);
                stockToSave.setHighestPrice(e7.getH());
                stockToSave.setLowestPrice(e7.getL());
                stockToSave.setOpenPrice(e7.getO());
                stockToSave.setVolumen(e7.getV());

                if(dateUtil.isToday(day) && !(dateUtil.isWeekend(day)) && !(stockBean.isHolidays(day))){
                    StockPriceDTO stockDb = this.getStockPrice(quote);
                    currentPrice = stockDb.getCurrentPrice();
                    previousClose = stockDb.getPreviousClosePrice();
                }else{
                    StockPrice stock = stockBean.getStockPrice(quote.getId(), day);
                    if(stock != null){
                        currentPrice = (stock.getCurrentPrice().equals(BigDecimal.ZERO))? BigDecimal.ZERO : stock.getCurrentPrice();
                        previousClose = (stock.getPreviousClosePrice().equals(BigDecimal.ZERO))? BigDecimal.ZERO : stock.getPreviousClosePrice();
                        closePrice = (stock.getClosePrice().equals(BigDecimal.ZERO))? BigDecimal.ZERO : stock.getClosePrice();
                    }else{
                        closePrice = e7.getC();
                    }
                }

                stockToSave.setClosePrice(closePrice);
                stockToSave.setCurrentPrice(currentPrice);
                stockToSave.setPreviousClosePrice(previousClose);
                stockBean.saveStockCandle(quote.getId(),day,stockToSave);

                log.info("EMA 7 value : {} to date : {}", e7.getEma(), day.format(DateTimeFormatter.ISO_LOCAL_DATE));
                log.info("EMA 30 value : {} to date : {}", e30.getEma(), day.format(DateTimeFormatter.ISO_LOCAL_DATE));
                log.info("SMA 200 value : {} to date : {}", s200.getSma(), day.format(DateTimeFormatter.ISO_LOCAL_DATE));
                if((dateUtil.isToday(day)) && !(dateUtil.isWeekend(day)) && !(stockBean.isHolidays(day))){
                    stockBean.saveEma(quote, e7.getEma(), 7, e7.getEma().compareTo(currentPrice), day);
                    stockBean.saveEma(quote, e30.getEma(), 30, e30.getEma().compareTo(currentPrice), day);
                    stockBean.saveSma(quote, s200.getSma(), 200, s200.getSma().compareTo(currentPrice), day);
                }else{
                    stockBean.saveEma(quote, e7.getEma(), 7, e7.getEma().compareTo(closePrice), day);
                    stockBean.saveEma(quote, e30.getEma(), 30, e30.getEma().compareTo(closePrice), day);
                    stockBean.saveSma(quote, s200.getSma(), 200, s200.getSma().compareTo(closePrice), day);
                }
                day = day.minus(1, ChronoUnit.DAYS);
                i++;
            }
        }else{
            throw new BadRequestException("Invalid EMAs values");
        }
    }


    public void getQuotesInTwoCandlesStrategy(){
        log.info("Getting list of all quotes in DB and all quotes that fulfill the strategy of two candles");
        List<Quote> quotes = stockBean.getAllQuotes();
        List<String> performTwoCandles = new ArrayList<>();

        quotes.forEach(quote->{
            /* Get quote prices */
            Candles candles = finnhubClient.getWeekCandles(quote.getQuote(),OffsetDateTime.now());
            List<BigDecimal> c = candles.getC();
            List<BigDecimal> o = candles.getO();
            if(c == null || c.isEmpty()){
                log.error("Could not find candles to stock : {}, please check if the ticker is correct", quote.getQuote());
                try {
                    messageService.notifyThreadException("Could not found candles of " + quote.getQuote());
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }

            }else{

                /*
                 *  -1 less than
                 *  0 equal to
                 *  or 1 greater
                 */
                int firstCandlePosition = c.get(c.size() - 1).compareTo(o.get(c.size() - 1));
                int secondCandlePosition = c.get(c.size() - 2).compareTo(o.get(c.size() - 2));

                if(firstCandlePosition != 0 && secondCandlePosition != 0){
                    if(firstCandlePosition == secondCandlePosition && firstCandlePosition > 0){
                        if(c.get(c.size() - 1).compareTo(c.get(c.size() - 2)) > 0){
                            log.info("Quote : {} - ID : {}, is presenting two positive candles in the same direction", quote.getQuote(), quote.getId());
                            /*try {
                                messageService.notifyQuote(quote, "positive");
                            } catch (IOException | InterruptedException e) {
                                e.printStackTrace();
                            }*/
                            performTwoCandles.add(quote.getQuote());
                        }
                    }else if (firstCandlePosition == secondCandlePosition && firstCandlePosition < 0){
                        if(c.get(c.size() - 1).compareTo(c.get(c.size() - 2)) < 0){
                            log.info("Quote : {} - ID : {}, is presenting two negative candles in the same direction", quote.getQuote(), quote.getId());
                            /*try {
                                messageService.notifyQuote(quote, "negative");
                            } catch (IOException | InterruptedException e) {
                                e.printStackTrace();
                            }*/
                            performTwoCandles.add(quote.getQuote());
                        }
                    }
                }

                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            messageService.notifyTwoCandles(performTwoCandles);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void getCrossoverAveragesStrategy(){

    }

}