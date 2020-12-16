package com.laddeep.financeapi.component;

import com.laddeep.financeapi.entity.db.Quote;
import com.laddeep.financeapi.entity.db.StockEarning;
import com.laddeep.financeapi.entity.db.StockFollowing;
import com.laddeep.financeapi.entity.db.StockPrice;
import com.laddeep.financeapi.integrations.finnhub.api.Earning;
import com.laddeep.financeapi.integrations.finnhub.api.StockPriceQuote;
import com.laddeep.financeapi.repository.StockEarningRepository;
import com.laddeep.financeapi.repository.StockFollowingRepository;
import com.laddeep.financeapi.repository.StockPriceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class StockBean {

    private StockPriceRepository stockPriceRepository;

    private StockFollowingRepository stockFollowingRepository;

    private ValidationBean validationBean;

    private StockEarningRepository earningRepository;

    public StockBean(
            StockPriceRepository stockPriceRepository,
            ValidationBean validationBean,
            StockFollowingRepository stockFollowingRepository,
            StockEarningRepository earningRepository
    ) {
        this.stockPriceRepository = stockPriceRepository;
        this.validationBean = validationBean;
        this.stockFollowingRepository = stockFollowingRepository;
        this.earningRepository = earningRepository;
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
            if(stock.getCurrentPrice().equals(BigDecimal.ZERO)){
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

    public void saveEarning(Earning earning, Long stockId){
        validationBean.notNull( "Quote", earning.getSymbol());
        StockEarning stockEarning = earningRepository.findByQuoteIdAndDate(stockId, OffsetDateTime.now(), OffsetDateTime.now());
        if(stockEarning == null){
            stockEarning = new StockEarning(
                    null,
                    stockId,
                    OffsetDateTime.now(),
                    earning.getEpsActual(),
                    earning.getEpsEstimate(),
                    earning.getQuarter(),
                    earning.getRevenueActual(),
                    earning.getRevenueEstimate()
            );
            earningRepository.save(stockEarning);
            log.info("Saving new earning : {} - date {}", earning.getSymbol(), stockEarning.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        }else if(stockEarning.getActualRevenue() == 0 && earning.getRevenueActual() != 0){
            stockEarning.setCurrentEps(earning.getEpsActual());
            stockEarning.setActualRevenue(earning.getRevenueActual());
            earningRepository.save(stockEarning);
        }
    }
}
