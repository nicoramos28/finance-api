package com.laddeep.financeapi.mapper;

import com.laddeep.financeapi.entity.api.StockPriceDTO;
import com.laddeep.financeapi.entity.db.StockPrice;
import com.laddeep.financeapi.integrations.finnhub.api.StockPriceQuote;
import org.springframework.stereotype.Component;

@Component
public class StockPriceDTOMapper extends Mapper<StockPriceQuote, StockPriceDTO>{

    @Override
    public StockPriceDTO map(StockPriceQuote apiResponse){
        return new StockPriceDTO(
                apiResponse.getC(),
                apiResponse.getH(),
                apiResponse.getL(),
                apiResponse.getO(),
                apiResponse.getPc(),
                apiResponse.getT()
        );
    }

}
