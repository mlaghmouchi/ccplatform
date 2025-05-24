package com.sqli.ccplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CcplatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(CcplatformApplication.class, args);
	}

}
