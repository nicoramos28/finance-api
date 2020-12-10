package com.laddeep.financeapi.controller.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @RequestMapping("/helloWorld")
    public String helloWorldRetreive(){
        return "Hello World!";
    }
}
