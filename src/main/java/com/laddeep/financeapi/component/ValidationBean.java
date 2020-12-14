package com.laddeep.financeapi.component;

import com.laddeep.financeapi.exceptions.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class ValidationBean {

    public void notNull(String field, Object o){
        if(o == null){
            throw new BadRequestException(String.format("Parameter '%s' can not be null", field));
        }
    }

}
