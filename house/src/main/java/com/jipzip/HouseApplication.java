package com.jipzip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.jipzip"})
public class HouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(HouseApplication.class, args);
	}

}
