package com.laddeep.financeapi.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;


@Slf4j
@Component
public class DateUtil {

    public OffsetDateTime timeStampToOffsetDateTime(Timestamp timestamp){
        Long epochSecond = timestamp.getTime()*1000;
        return OffsetDateTime.ofInstant(Instant.ofEpochMilli(epochSecond),ZoneId.systemDefault());
    }
}
