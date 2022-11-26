package com.sai.incubation.IotConnector.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sai.incubation.IotConnector.utility.ObjectMapperUtil;

@Configuration
public class IotConnectorConfig {

	@Bean
	@Primary
	public MappingJackson2HttpMessageConverter mappingJaskson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objectMapper = mappingJackson2HttpMessageConverter.getObjectMapper();
		ObjectMapperUtil.addJacksonMixins(objectMapper);
		return mappingJackson2HttpMessageConverter;
	}
	
}
