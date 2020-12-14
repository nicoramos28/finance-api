package com.laddeep.financeapi.exceptions;

import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "ID given not found")
public class NotFoundException extends AppException{

    public NotFoundException(Class<?> c, String id) {
        super(String.format("Entity '%s' not found by id '%s'", c.getSimpleName(), id));
    }

    public NotFoundException(Class<?> c, Number id) {
        super(String.format("Entity '%s' not found by id '%s'", c.getSimpleName(), id));
    }

    public NotFoundException(String... params) {
        super(String.format("%s not found", StringUtils.join(params)));
    }
    //agrego el "String... params" que puede obtener un arreglo de elementos en lugar de un único elemento,
    // por eso posteriormente la excepcion usa un join de los mismos.

}
