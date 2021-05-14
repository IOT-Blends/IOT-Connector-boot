package com.sai.incubation.IotConnector.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import static com.sai.incubation.IotConnector.constants.SecurityConstant.*;
import com.sai.incubation.IotConnector.utility.JwtUtil;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter{
	
	private JwtUtil jwtUtil;

	public JwtAuthorizationFilter(JwtUtil jwtUtil) {
		super();
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getMethod().equalsIgnoreCase(OPTIONS_HTTP_METHOD))
			response.setStatus(HttpStatus.OK.value());
		else {
			String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
			if(authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_PREFIX)) {
				filterChain.doFilter(request, response);
				return;
			}
			
			String token = authorizationHeader.substring(TOKEN_PREFIX.length());
			String username = jwtUtil.getSubject(token);
			
			if(jwtUtil.isTokenValid(username, token)) {
				List<GrantedAuthority> authorities = jwtUtil.getAuthorities(token);
				Authentication authentication = jwtUtil.getAuthentication(username, authorities, request);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} else {
				SecurityContextHolder.clearContext();
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
