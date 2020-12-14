package com.laddeep.financeapi.service;

import com.laddeep.financeapi.api.stockPrice.StockPriceDTO;
import com.laddeep.financeapi.entity.db.Quote;
import com.laddeep.financeapi.mapper.StockPriceDTOMapper;
import com.laddeep.financeapi.integrations.finnhub.FinnhubClient;
import com.laddeep.financeapi.integrations.finnhub.api.StockPriceQuote;
import org.springframework.stereotype.Component;

@Component
public class StockPriceService {

    private StockPriceDTOMapper stockPriceMapper;

    private FinnhubClient finnhubClient;

    public StockPriceService(
            StockPriceDTOMapper quoteDtoMapper,
            FinnhubClient finnhubClient
    ) {
        this.stockPriceMapper = quoteDtoMapper;
        this.finnhubClient = finnhubClient;
    }

    public StockPriceDTO GetStockPriceQuote(Quote quote){
        StockPriceQuote response = this.finnhubClient.getStockPriceQuote(quote.getQuote());
        return stockPriceMapper.map(response);
    }
}
