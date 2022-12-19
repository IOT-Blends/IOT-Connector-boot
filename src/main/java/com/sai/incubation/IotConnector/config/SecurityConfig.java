
package com.sai.incubation.IotConnector.config;

import java.util.Arrays;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.sai.incubation.IotConnector.constants.SecurityConstant;
import com.sai.incubation.IotConnector.filter.JwtAccessDeniedHandler;
import com.sai.incubation.IotConnector.filter.JwtAuthEntryPointFilter;
import com.sai.incubation.IotConnector.filter.JwtAuthorizationFilter;

//@Profile("LOCAL")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private JwtAuthorizationFilter jwtAuthFilter;
	private JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private JwtAuthEntryPointFilter jwtAuthEntryPoint;
	private UserDetailsService userDetailsService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public SecurityConfig(JwtAuthorizationFilter jwtAuthFilter, JwtAccessDeniedHandler jwtAccessDeniedHandler,
			JwtAuthEntryPointFilter jwtAuthEntryPoint, @Qualifier("userDetailsService")UserDetailsService userDetailsService,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.jwtAuthFilter = jwtAuthFilter;
		this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
		this.jwtAuthEntryPoint = jwtAuthEntryPoint;
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/**
	 * /myAccount - Secured /myBalance - Secured /myLoans - Secured /myCards -
	 * Secured /notices - Not Secured /contact - Not Secured
	 */

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().configurationSource(new CorsConfigurationSource() {

			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration config = new CorsConfiguration();
//				config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
				config.setAllowedOrigins(Collections.singletonList("*"));
				config.setAllowedMethods(Collections.singletonList("*"));
				config.setAllowCredentials(true);
				config.setAllowedHeaders(Collections.singletonList("*"));
				config.setMaxAge(3600L);
				return config;
			}
		}).and().csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().authorizeRequests().antMatchers(SecurityConstant.PUBLIC_URLS).permitAll()
		.anyRequest().authenticated() 
		//.anyRequest().permitAll()
		.and().exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler)
		.authenticationEntryPoint(jwtAuthEntryPoint)
		.and().addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

	}

	protected CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);

		config.setAllowedOrigins(Collections.singletonList("*"));
		config.setAllowedHeaders(Collections.singletonList("*"));
		config.addExposedHeader("Jwt-Token");
		config.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "OPTIONS", "DELETE"));
		source.registerCorsConfiguration("/**", config);

		return new CorsFilter(source);

	}
	
}
