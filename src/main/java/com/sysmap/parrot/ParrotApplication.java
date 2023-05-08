 package com.sysmap.parrot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.kafka.annotation.EnableKafka;


 @EnableKafka
 @SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ParrotApplication {


	public static void main(String[] args) {
		SpringApplication.run(ParrotApplication.class, args);
	}

}
