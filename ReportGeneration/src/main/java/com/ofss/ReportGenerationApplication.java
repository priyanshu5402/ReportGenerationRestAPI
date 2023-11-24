package com.ofss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReportGenerationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportGenerationApplication.class, args);
		System.out.println("Application Started");
	}

}
