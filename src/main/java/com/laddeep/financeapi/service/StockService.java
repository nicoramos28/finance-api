package com.laddeep.financeapi.service;

import com.laddeep.financeapi.api.stockPrice.StockPriceDTO;
import com.laddeep.financeapi.component.QuoteBean;
import com.laddeep.financeapi.component.StockBean;
import com.laddeep.financeapi.entity.db.Quote;
import com.laddeep.financeapi.exceptions.BadRequestException;
import com.laddeep.financeapi.mapper.StockPriceDTOMapper;
import com.laddeep.financeapi.integrations.finnhub.FinnhubClient;
import com.laddeep.financeapi.integrations.finnhub.api.StockPriceQuote;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
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
}
