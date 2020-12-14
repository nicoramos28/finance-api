package com.laddeep.financeapi.component;

import com.laddeep.financeapi.entity.db.Quote;
import com.laddeep.financeapi.repository.QuoteRepository;
import org.springframework.stereotype.Component;
import java.time.OffsetDateTime;

@Component
public class QuoteBean {

    private QuoteRepository quoteRepository;

    private ValidationBean validationBean;

    public QuoteBean(QuoteRepository quoteRepository, ValidationBean validationBean) {
        this.quoteRepository = quoteRepository;
        this.validationBean = validationBean;
    }

    public Quote get(String ticker){
        validationBean.notNull("Quote", ticker);
        Quote quote = quoteRepository.findByQuote(ticker);
        if(quote == null){
            quote = new Quote(null,ticker,OffsetDateTime.now());
        }else{
            quote.setLastUpdate(OffsetDateTime.now());
        }
        quoteRepository.save(quote);
        return quote;
    }

}
