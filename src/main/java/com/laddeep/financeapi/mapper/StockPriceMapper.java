package com.laddeep.financeapi.mapper;

import com.laddeep.financeapi.entity.api.StockPriceDTO;
import com.laddeep.financeapi.entity.db.StockPrice;

public class StockPriceMapper extends Mapper<StockPrice, StockPriceDTO>{

    @Override
    public StockPriceDTO map(StockPrice stockPrice){
        return new StockPriceDTO(
            stockPrice.getCurrentPrice(),
                stockPrice.getHighestPrice(),
                stockPrice.getLowestPrice(),
                stockPrice.getOpenPrice(),
                stockPrice.getPreviousClosePrice(),
                stockPrice.getTime()
        );
    }
}
