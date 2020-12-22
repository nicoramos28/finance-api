package com.laddeep.financeapi.service;

import com.laddeep.financeapi.entity.api.*;
import com.laddeep.financeapi.component.QuoteBean;
import com.laddeep.financeapi.component.StockBean;
import com.laddeep.financeapi.entity.db.Quote;
import com.laddeep.financeapi.entity.db.StockPrice;
import com.laddeep.financeapi.exceptions.BadRequestException;
import com.laddeep.financeapi.mapper.StockPriceDTOMapper;
import com.laddeep.financeapi.integrations.finnhub.FinnhubClient;
import com.laddeep.financeapi.integrations.finnhub.api.StockPriceQuote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Component
@Slf4j
public class StockService{

    private StockPriceDTOMapper stockPriceMapper;

    private FinnhubClient finnhubClient;

    private StockBean stockBean;

    private QuoteBean quoteBean;

    public StockService(
            StockPriceDTOMapper quoteDtoMapper,
            FinnhubClient finnhubClient,
            StockBean stockBean,
            QuoteBean quoteBean
    ) {
        this.stockPriceMapper = quoteDtoMapper;
        this.finnhubClient = finnhubClient;
        this.stockBean = stockBean;
        this.quoteBean = quoteBean;
    }

    public StockPriceDTO getStockPriceQuote(Quote quote){
        StockPriceQuote response = this.finnhubClient.getStockPriceQuote(quote.getQuote());
        if(response != null){
            if(response.getC().compareTo(BigDecimal.ZERO) != 0){
                stockBean.get(quote, response, null);
                return stockPriceMapper.map(response);
            }else{
                throw new BadRequestException("Ticker could not be found");
            }
        }else{
            throw new BadRequestException("Invalid Request");
        }
    }

    public Long saveStockToFollow(Quote quote){
        return stockBean.getFollow(quote);
    }

    public void geStockEmaValues(String ticker, Integer timePeriod){
        Quote quote = quoteBean.get(ticker);
        //Getting current stock price
        StockPriceDTO stockPrice = this.getStockPriceQuote(quote);
        EmaDTO ema7 = this.finnhubClient.getMovingAverage(quote.getQuote(), OffsetDateTime.now(), EmaDTO.class, 7);
        EmaDTO ema30 = this.finnhubClient.getMovingAverage(quote.getQuote(), OffsetDateTime.now(), EmaDTO.class, 30);
        log.info("EMA 7 value : {}", ema7.get());
        stockBean.saveEma(quote, ema7.get(), 7, ema7.get().compareTo(stockPrice.getCurrentPrice()));

        log.info("EMA 30 value : {}", ema30.get());
        stockBean.saveEma(quote, ema30.get(), 30, ema30.get().compareTo(stockPrice.getCurrentPrice()));

        if(timePeriod != null){
            EmaDTO ema = this.finnhubClient.getMovingAverage(quote.getQuote(), OffsetDateTime.now(), EmaDTO.class, timePeriod);
            stockBean.saveEma(quote, ema.get(), timePeriod, ema7.get().compareTo(stockPrice.getCurrentPrice()));
            log.info("EMA {} value : {}", timePeriod, ema.get());
        }
    }

    public void geStockSmaValues(String ticker, Integer timePeriod){
        Quote quote = quoteBean.get(ticker);
        //Getting current stock price
        StockPriceDTO stockPrice = this.getStockPriceQuote(quote);
        SmaDTO sma200 = this.finnhubClient.getMovingAverage(quote.getQuote(), OffsetDateTime.now(), SmaDTO.class, 200);
        log.info("SMA 200 value : {}", sma200.get());
        stockBean.saveSma(quote, sma200.get(), 200, sma200.get().compareTo(stockPrice.getCurrentPrice()));

        if(timePeriod != null){
            SmaDTO sma = this.finnhubClient.getMovingAverage(quote.getQuote(), OffsetDateTime.now(), SmaDTO.class, timePeriod);
            stockBean.saveSma(quote, sma.get(), timePeriod, sma200.get().compareTo(stockPrice.getCurrentPrice()));
            log.info("SMA {} value : {}", timePeriod, sma.get());
        }
    }

    public void getCandlesForStock(String ticker){
        Quote quote = quoteBean.get(ticker);
        Candle candles = this.finnhubClient.StockWeekCandles(ticker, OffsetDateTime.now());
        if(candles != null){
            int i = 0;
            OffsetDateTime day = OffsetDateTime.now().minus(7, ChronoUnit.DAYS);
            while(i < candles.getV().size()){
                if((day.getDayOfWeek().name().equals("SATURDAY"))) {
                    day = day.plus(2, ChronoUnit.DAYS);
                }else if((day.getDayOfWeek().name().equals("SUNDAY")) || quoteBean.isHolidays(day)){
                    day = day.plus(1, ChronoUnit.DAYS);
                }
                StockPrice candle = new StockPrice();
                BigDecimal currentPrice = BigDecimal.ZERO;
                BigDecimal previousClosePrice = BigDecimal.ZERO;
                if(day.format(DateTimeFormatter.ISO_LOCAL_DATE).equals(OffsetDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE))){
                    StockPriceDTO stockPrice = this.getStockPriceQuote(quote);
                    currentPrice = stockPrice.getCurrentPrice();
                    previousClosePrice = stockPrice.getPreviousClosePrice();
                }
                candle.setQuoteId(quote.getId());
                candle.setOpenPrice(candles.getO().get(i));
                candle.setClosePrice(candles.getC().get(i));
                candle.setCurrentPrice(currentPrice);
                candle.setHighestPrice(candles.getH().get(i));
                candle.setLowestPrice(candles.getL().get(i));
                candle.setPreviousClosePrice(previousClosePrice);
                candle.setTime(day);
                candle.setVolumen(candles.getV().get(i));
                stockBean.saveCandle(quote, candle, day);
                day = day.plus(1, ChronoUnit.DAYS);
                i ++;
            }
        }else{
            throw new BadRequestException("Invalid Request");
        }
    }
}




