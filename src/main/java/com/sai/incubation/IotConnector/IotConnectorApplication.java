package com.sai.incubation.IotConnector;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sai.incubation.IotConnector.constants.FileConstant;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableMongoRepositories("com.sai.incubation.IotConnector.repository")
/* @ComponentScan("com.sai.incubation.IotConnector.*") */
public class IotConnectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(IotConnectorApplication.class, args);
		// To generate directories in any system unix/windows 
		new File(FileConstant.USER_FOLDER).mkdirs();
	}
	
	// Create encoder when ever the application starts 
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
