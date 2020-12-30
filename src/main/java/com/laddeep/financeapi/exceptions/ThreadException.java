package com.laddeep.financeapi.exceptions;

import com.laddeep.financeapi.service.TelegramMessageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class ThreadException extends AppException{

    @Autowired
    private TelegramMessageService messageService;

    public ThreadException(String message) throws IOException, InterruptedException {
        super(message);
        messageService.notifyThreadException(message);
    }

}
