package com.sai.incubation.IotConnector.filter;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sai.incubation.IotConnector.constants.SecurityConstant;
import com.sai.incubation.IotConnector.domain.Common.CommonResponseEntity;

@Component
public class JwtAuthEntryPointFilter extends Http403ForbiddenEntryPoint{
	private static final Log logger = LogFactory.getLog(JwtAuthEntryPointFilter.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException arg2) throws IOException {
		CommonResponseEntity httpResponse = new CommonResponseEntity(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN, HttpStatus.FORBIDDEN.getReasonPhrase().toUpperCase(), SecurityConstant.FORBIDDEN_MSG);
		logger.info("Forbidden/Denied Access");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.FORBIDDEN.value());
		OutputStream outputStream = response.getOutputStream();
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(outputStream, httpResponse);
		outputStream.flush();
	}
}
