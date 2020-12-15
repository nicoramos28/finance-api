package com.laddeep.financeapi.component;

import com.laddeep.financeapi.entity.db.Quote;
import com.laddeep.financeapi.entity.db.StockFollowing;
import com.laddeep.financeapi.entity.db.StockPrice;
import com.laddeep.financeapi.integrations.finnhub.api.StockPriceQuote;
import com.laddeep.financeapi.repository.StockFollowingRepository;
import com.laddeep.financeapi.repository.StockPriceRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Component
public class StockBean {

    private StockPriceRepository stockPriceRepository;

    private StockFollowingRepository stockFollowingRepository;

    private ValidationBean validationBean;

    public StockBean(
            StockPriceRepository stockPriceRepository,
            ValidationBean validationBean,
            StockFollowingRepository stockFollowingRepository
    ) {
        this.stockPriceRepository = stockPriceRepository;
        this.validationBean = validationBean;
        this.stockFollowingRepository = stockFollowingRepository;
    }

    public void get(Quote quote, StockPriceQuote response) {
        validationBean.notNull("StockPrices", response);
        StockPrice stock = stockPriceRepository.findByQuoteIdAndTime(quote.getId(), OffsetDateTime.now());
        if(stock == null){
            stock = new StockPrice(
                    null,
                    quote.getId(),
                    response.getC(),
                    response.getH(),
                    response.getL(),
                    response.getO(),
                    response.getPc(),
                    OffsetDateTime.now()
            );
        }else{
            if(stock.getCurrentPrice()==BigDecimal.ZERO){
                stock.setCurrentPrice(response.getC());
                stock.setHighestPrice(response.getH());
                stock.setLowestPrice(response.getL());
                stock.setOpenPrice(response.getO());
                stock.setPreviousClosePrice(response.getPc());
            }
            stock.setCurrentPrice(response.getC());
            stock.setHighestPrice(response.getH());
            stock.setLowestPrice(response.getL());
        }
        stockPriceRepository.save(stock);
    }

    public Long getFollow(Quote quote){
        validationBean.notNull("Quote", quote.getQuote());
        StockFollowing stock = stockFollowingRepository.findByQuote(quote.getQuote());
        if(stock == null){
            stock = new StockFollowing(
                    null,
                    quote.getId(),
                    quote.getQuote(),
                    OffsetDateTime.now()
            );
        }else{
            stock.setLastUpdate(OffsetDateTime.now());
        }
        stockFollowingRepository.save(stock);
        return stock.getId();
    }
}
