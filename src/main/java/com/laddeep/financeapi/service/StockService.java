package com.laddeep.financeapi.service;

import com.laddeep.financeapi.entity.api.EmaDTO;
import com.laddeep.financeapi.entity.api.SmaDTO;
import com.laddeep.financeapi.entity.api.StockPriceDTO;
import com.laddeep.financeapi.component.QuoteBean;
import com.laddeep.financeapi.component.StockBean;
import com.laddeep.financeapi.entity.db.Quote;
import com.laddeep.financeapi.exceptions.BadRequestException;
import com.laddeep.financeapi.mapper.StockPriceDTOMapper;
import com.laddeep.financeapi.integrations.finnhub.FinnhubClient;
import com.laddeep.financeapi.integrations.finnhub.api.StockPriceQuote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Component
@Slf4j
public class StockService {

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
                stockBean.get(quote, response);
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
}