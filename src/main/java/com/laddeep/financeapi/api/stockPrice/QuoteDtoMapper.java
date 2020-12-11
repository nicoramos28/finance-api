package com.laddeep.financeapi.api.stockPrice;

import com.laddeep.financeapi.integrations.finnhub.api.StockPriceQuote;
import org.springframework.stereotype.Component;

@Component
public class QuoteDtoMapper {

    public QuoteDto QuouteDtoMapper(StockPriceQuote apiResponse){
        return new QuoteDto(
                apiResponse.getC(),
                apiResponse.getH(),
                apiResponse.getL(),
                apiResponse.getO(),
                apiResponse.getPc(),
                apiResponse.getT()
        );
    }
}
