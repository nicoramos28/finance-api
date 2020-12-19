package com.laddeep.financeapi.service;

import com.laddeep.financeapi.entity.api.EmaDTO;
import com.laddeep.financeapi.entity.api.StockPriceDTO;
import com.laddeep.financeapi.component.QuoteBean;
import com.laddeep.financeapi.component.StockBean;
import com.laddeep.financeapi.entity.db.Quote;
import com.laddeep.financeapi.entity.db.StockEma;
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

    public void geStockEmaValues(String ticker, String timePeriod){
        Quote quote = quoteBean.get(ticker);
        EmaDTO ema7 = this.finnhubClient.getMovingAverage(quote.getQuote(), OffsetDateTime.now(), "ema", "7");
        EmaDTO ema30 = this.finnhubClient.getMovingAverage(quote.getQuote(), OffsetDateTime.now(), "ema", "30");
        log.info("EMA 7 value : {}", ema7.get());
        stockBean.saveEma(quote, ema7.get(), 7);
        log.info("EMA 30 value : {}", ema30.get());
        stockBean.saveEma(quote, ema30.get(), 30);
    }

}