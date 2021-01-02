package com.laddeep.financeapi.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


@Slf4j
@Component
public class DateUtil {

    public OffsetDateTime timeStampToOffsetDateTime(Timestamp timestamp){
        Long epochSecond = timestamp.getTime()*1000;
        return OffsetDateTime.ofInstant(Instant.ofEpochMilli(epochSecond),ZoneId.systemDefault());
    }

    public boolean isSaturday(OffsetDateTime date){
        if(date.getDayOfWeek().name().equals("SATURDAY")){
            return true;
        }
        return false;
    }

    public boolean isSunday(OffsetDateTime date){
        if(date.getDayOfWeek().name().equals("SUNDAY")){
            return true;
        }
        return false;
    }

    public boolean isToday(OffsetDateTime date){
        if(date.format(DateTimeFormatter.ISO_LOCAL_DATE).equals(
                OffsetDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE))){
            return true;
        }
        return false;
    }

    public boolean isWeekend(OffsetDateTime date){
        if((date.getDayOfWeek().name().equals("SATURDAY")) || (date.getDayOfWeek().name().equals("SUNDAY"))){
            return true;
        }
        return false;
    }
}