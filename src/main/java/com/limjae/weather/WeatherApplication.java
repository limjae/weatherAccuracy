package com.limjae.weather;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.TimeZone;

@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling
@Slf4j
public class WeatherApplication {
	@PostConstruct
	public void setTimeZone(){
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
		Locale.setDefault(Locale.KOREA);
		log.info("Server Time : {}", LocalDateTime.now());
	}

	public static void main(String[] args) {
		SpringApplication.run(WeatherApplication.class, args);
	}

}
