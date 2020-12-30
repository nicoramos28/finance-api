package com.laddeep.financeapi.component;

import com.laddeep.financeapi.entity.db.Quote;
import com.laddeep.financeapi.entity.db.StockExchangeHoliday;
import com.laddeep.financeapi.exceptions.PersistenceException;
import com.laddeep.financeapi.repository.ExchangeHolidaysRepository;
import com.laddeep.financeapi.repository.QuoteRepository;
import org.springframework.stereotype.Component;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class QuoteBean {

    private QuoteRepository quoteRepository;

    private ValidationBean validationBean;

    private ExchangeHolidaysRepository holidaysRepository;

    public QuoteBean(QuoteRepository quoteRepository, ValidationBean validationBean, ExchangeHolidaysRepository holidaysRepository) {
        this.quoteRepository = quoteRepository;
        this.validationBean = validationBean;
        this.holidaysRepository = holidaysRepository;
    }

    public Quote get(String ticker){
        Quote quote;
        validationBean.notNull("Quote", ticker);
        try{
            quote = quoteRepository.findByQuote(ticker);
            if(quote == null){
                quote = new Quote(null,ticker,OffsetDateTime.now());
            }else{
                quote.setLastUpdate(OffsetDateTime.now());
            }
            quoteRepository.save(quote);
        }catch (PersistenceException e){
            throw new PersistenceException("Error trying to connect to stocks information");
        }
        return quote;
    }

    public Boolean isHolidays(OffsetDateTime today){
        StockExchangeHoliday holiday = holidaysRepository.findByDate(today.format(DateTimeFormatter.ISO_LOCAL_DATE));
        return holiday != null;
    }
}
