package com.laddeep.financeapi.mapper;

import com.laddeep.financeapi.entity.api.QuoteDTO;
import com.laddeep.financeapi.entity.db.Quote;

public class QuoteMapper extends Mapper<Quote, QuoteDTO>{

    @Override
    public QuoteDTO map(Quote quote){
        return new QuoteDTO(
                quote.getId(),
                quote.getQuote(),
                quote.getLastUpdate()
        );
    }
}
