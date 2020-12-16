package com.laddeep.financeapi;

import com.laddeep.financeapi.service.EarningService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

@EnableScheduling
@SpringBootApplication
@Slf4j
public class FinanceApiApplication {

	@Autowired
	EarningService earningService;

	public static void main(String[] args) {
		SpringApplication.run(FinanceApiApplication.class, args);
	}

	@Scheduled(fixedRate = 10000L)
	public void financeApiRunner() throws IOException, InterruptedException {
		log.info("Runner started");
		//My logic
		earningService.getDailyEarning();
		log.info("Runner finished");
	}
}
