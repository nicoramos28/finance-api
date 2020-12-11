package com.laddeep.financeapi.service;

import com.laddeep.financeapi.api.stockPrice.QuoteDto;
import com.laddeep.financeapi.api.stockPrice.QuoteDtoMapper;
import com.laddeep.financeapi.integrations.finnhub.FinnhubClient;
import com.laddeep.financeapi.integrations.finnhub.api.StockPriceQuote;
import org.springframework.stereotype.Component;

@Component
public class StockPriceService {

    private QuoteDtoMapper quoteMapper;

    private FinnhubClient finnhubClient;

    public StockPriceService(
        QuoteDtoMapper quoteDtoMapper,
        FinnhubClient finnhubClient
    ){
        this.quoteMapper = quoteDtoMapper;
        this.finnhubClient = finnhubClient;
    }


    public QuoteDto GetStockPriceQuote(String quote){
        StockPriceQuote response = this.finnhubClient.getStockPriceQuote(quote);
        return quoteMapper.QuouteDtoMapper(response);
    }
}
