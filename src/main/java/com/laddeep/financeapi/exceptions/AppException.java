package com.laddeep.financeapi.exceptions;

public abstract class AppException extends RuntimeException{

    public AppException(String message){ super(message);}

    public AppException(String message, Throwable cause){ super(message, cause);}
}
