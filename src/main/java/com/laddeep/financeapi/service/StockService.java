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
import com.laddeep.financeapi.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.io.IOException;
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

    private TelegramMessageService messageService;

    private DateUtil dateUtil;

    public StockService(
            StockPriceDTOMapper quoteDtoMapper,
            FinnhubClient finnhubClient,
            StockBean stockBean,
            QuoteBean quoteBean,
            TelegramMessageService messageService,
            DateUtil dateUtil
    ) {
        this.stockPriceMapper = quoteDtoMapper;
        this.finnhubClient = finnhubClient;
        this.stockBean = stockBean;
        this.quoteBean = quoteBean;
        this.messageService = messageService;
        this.dateUtil = dateUtil;
    }

    public StockPriceDTO getStockPriceQuote(Quote quote){
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
                return stockPriceMapper.map(response);
            }
        }else{
            throw new BadRequestException("Invalid Request");
        }
    }

    public Long saveStockToFollow(Quote quote){
        return stockBean.saveStockToFollow(quote);
    }

    public void geStockEmaSmaValues(String ticker){
        Quote quote = quoteBean.get(ticker);
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
                while(dateUtil.isWeekend(day) || quoteBean.isHolidays(day)){
                    if(dateUtil.isSaturday(day) || quoteBean.isHolidays(day)) {
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

                if(dateUtil.isToday(day) && !(dateUtil.isWeekend(day)) && !(quoteBean.isHolidays(day))){
                    StockPriceDTO stockDb = this.getStockPriceQuote(quote);
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
                if((dateUtil.isToday(day)) && !(dateUtil.isWeekend(day)) && !(quoteBean.isHolidays(day))){
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

}