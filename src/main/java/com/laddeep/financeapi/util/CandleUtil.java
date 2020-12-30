package com.laddeep.financeapi.util;

import com.laddeep.financeapi.entity.db.StockPrice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class CandleUtil {

    /**
     *
     * @param candle
     * @return int
     * Logic to compare
     * BigDecimal CompareTO
     *  0 == equal                      - StarCandle
     *  1 == first value is greater     - GreenCandle
     * -1 == second values is greater   - RedCandle
     */
    public int getCandleType(StockPrice candle){

        boolean isCurrentDate = candle.getTime().format(DateTimeFormatter.ISO_LOCAL_DATE).equals
                (OffsetDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        if(isCurrentDate){
            return candle.getCurrentPrice().compareTo(candle.getOpenPrice());
        }else{
            return candle.getClosePrice().compareTo(candle.getOpenPrice());
        }
    }

    public boolean findCandlesGap(StockPrice c1, StockPrice c2){
        boolean thereIsGap = false;
        if((c2.getHighestPrice().compareTo(c1.getLowestPrice()) == -1)
                || (c2.getLowestPrice().compareTo(c1.getHighestPrice()) == 1)){
            thereIsGap = true;
        }
        return thereIsGap;
    }

}
