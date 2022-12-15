package com.mosippe.ussd_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
//@EntityScan("com.mosippe.ussd_api.DTO.SessionDTO")
public class UssdApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UssdApiApplication.class, args);
	}

}
