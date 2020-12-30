package com.laddeep.financeapi;

import com.laddeep.financeapi.runner.Monitor;
import com.laddeep.financeapi.runner.SenderFARunner;
import com.laddeep.financeapi.runner.TriggerFARunner;
import com.laddeep.financeapi.service.StockAnalyticsService;
import com.laddeep.financeapi.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@SpringBootApplication
@Slf4j
public class FinanceApiApplication {

	@Autowired
	StockService stockService;

	@Autowired
	StockAnalyticsService analyticsService;

	public static void main(String[] args) {
		SpringApplication.run(FinanceApiApplication.class, args);
	}

	//@Scheduled(fixedDelay = 900000L)
	@Scheduled(cron = "0 10/55 * ? * *")
	public void earningsRunner(){
		log.info("\n######################################## Start Finance-API Runner ########################################");
		Monitor monitor = new Monitor();
		Thread sender = new Thread(new SenderFARunner(monitor));
		Thread receive = new Thread(new TriggerFARunner(monitor, stockService, analyticsService));
		sender.start();
		receive.start();
	}
}
