package com.laddeep.financeapi.integrations.finnhub;

import com.laddeep.financeapi.entity.api.Candle;
import com.laddeep.financeapi.entity.api.EmasDTO;
import com.laddeep.financeapi.entity.api.SmasDTO;
import com.laddeep.financeapi.exceptions.BadRequestException;
import com.laddeep.financeapi.integrations.finnhub.api.EarningsCalendar;
import com.laddeep.financeapi.integrations.finnhub.api.StockPriceQuote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


@Slf4j
@Service
public class FinnhubClient {

    @Value("${finnhub.url}")
    private String URL;

    @Value("${finnhub.base.url}")
    private String BASE_URL;

    @Value("${finnhub.token}")
    private String TOKEN;

    public RestTemplate restTemplate;

    FinnhubClient(){
        this.restTemplate = new RestTemplate();
    }

    public <R> ResponseEntity<R> get(String url, Class<R> responseType){
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Finnhub-Token", TOKEN);
        HttpEntity<String> entity = new HttpEntity<>("", headers);
        log.info("GET URL Request : {}", url);
        log.info("Headers : {}", headers);
        ResponseEntity<R> response = this.restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
        log.info("Finnhub api response : {}", response);
        if(response.getBody() != null && response.getStatusCode().is2xxSuccessful()){
            return response;
        }
        return null;
    }

    /**
     * Stock Price Quote
     * https://finnhub.io/api/v1/quote?symbol=AAPL&token=
     * @param quote
     * @return stockPriceQuote
     */
    public StockPriceQuote getStockPriceQuote(String quote){
        try{
            ResponseEntity<StockPriceQuote> dto = this.get(URL + BASE_URL + "/quote?symbol=" + quote,
                    StockPriceQuote.class);
            log.info("Finnhub response - {} Stock Price: {}",quote, dto);
            if(dto != null){
                return dto.getBody();
            }
        }catch (BadRequestException e){
            throw new BadRequestException(e.getMessage());
        }
        return null;
    }

    /**
     * Candles Price
     * https://finnhub.io/api/v1/stock/candle?symbol=AAPL&resolution=D&from=1605543327&to=1605629727
     * @param quote
     * @param toDate
     * @return
     */
    public Candle StockWeekCandles(String quote, OffsetDateTime toDate){
        log.info("Getting Candles of : {} - until : {}", quote, toDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
        try{
            ResponseEntity<Candle> candles = this.get(URL + BASE_URL
                    + "/stock/candle?symbol=" + quote + "&resolution=D&from=" + toDate.minus(7, ChronoUnit.DAYS).toEpochSecond()
                    + "&to=" + toDate.toEpochSecond(), Candle.class);

            if(candles.getBody() != null){
                return candles.getBody();
            }
        }catch (BadRequestException e){
            throw new BadRequestException(e.getMessage());
        }
        return null;
    }

    /**
     * Earnings Calendar
     * https://finnhub.io/api/v1/calendar/earnings?from=2020-03-12&to=2020-03-15
     * @param fromDate
     * @param toDate
     * @return
     */
    public EarningsCalendar getEarnings(OffsetDateTime fromDate, OffsetDateTime toDate){
        log.info("Getting earnings from : {} - to : {}", fromDate.format(DateTimeFormatter.ISO_LOCAL_DATE), toDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
        try{
            ResponseEntity<EarningsCalendar> earnings = this.get(URL + BASE_URL
                    + "/calendar/earnings?from=" + fromDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
                    + "&to=" + toDate.format(DateTimeFormatter.ISO_LOCAL_DATE), EarningsCalendar.class);

            if(earnings.getBody() != null){
                return earnings.getBody();
            }
        }catch (BadRequestException e){
            throw new BadRequestException(e.getMessage());
        }
        return null;
    }

    /**
     * Technical Indicators
     * https://finnhub.io/api/v1/indicator?symbol=AAPL&resolution=D&from=1583098857&to=1584308457&indicator=sma&timeperiod=3
     * @param quote
     * @param toDate
     * @param R
     * @param timePeriod
     * @param <R>
     * @return
     */
    public <R> R getMovingAverage(String quote, OffsetDateTime toDate, Class R, int timePeriod){
        Long from, to = toDate.toEpochSecond();
        switch (timePeriod){
            case 7: from = toDate.minus(2, ChronoUnit.MONTHS).toEpochSecond();
                break;
            case 30 : from = toDate.minus(4, ChronoUnit.MONTHS).toEpochSecond();
            break;
            case 200 : from = toDate.minus(1, ChronoUnit.YEARS).toEpochSecond();
            break;
            default:
                throw new BadRequestException("Incorrect time period to EMA");
        }
        if(R == EmasDTO.class){
            log.info("Getting EMA values");
            try{
                ResponseEntity<EmasDTO> emaValues = this.get(
                        URL + BASE_URL + "/indicator?symbol=" + quote + "&resolution=D"
                                + "&from=" + from + "&to=" + to + "&indicator=ema&timeperiod=" + timePeriod,
                        R
                );
                if(emaValues.getBody() != null){
                    return (R) emaValues.getBody();
                }
            }catch (BadRequestException e){
                throw new BadRequestException(e.getMessage());
            }
        }else if(R == SmasDTO.class){
            log.info("Getting SMA values");
            try{
                ResponseEntity<SmasDTO> smaValues = this.get(
                        URL + BASE_URL + "/indicator?symbol=" + quote + "&resolution=D"
                                + "&from=" + from + "&to=" + to + "&indicator=sma&timeperiod=" + timePeriod,
                        R
                );
                if(smaValues.getBody() != null){
                    return (R) smaValues.getBody();
                }
            }catch (BadRequestException e){
                throw new BadRequestException(e.getMessage());
            }
        }
        return null;
    }
}

