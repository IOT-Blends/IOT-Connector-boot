package com.sai.incubation.IotConnector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("com.sai.incubation.IotConnector.repository")
/* @ComponentScan("com.sai.incubation.IotConnector.*") */
public class IotConnectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(IotConnectorApplication.class, args);
	}

}
