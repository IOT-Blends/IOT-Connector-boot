package com.sai.incubation.IotConnector.constants;

public class SecurityConstant {

	public static final long EXPIRATION_TIME = 432_000_000; //5 days expressed in milliseconds
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String JWT_TOKEN_HEADER = "Jwt-Token";
	public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
	public static final String IOT_BLENDS_LLC = "Iot Blends, LLC"; // Token issued by
	public static final String AUTHORITIES = "authorities";
	public static final String FORBIDDEN_MSG = "Please logon to access this resource";
	public static final String ACCESS_DENIED_MSG = "You don't have enough persmission to access this resource";
	public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
	public static final String[] PUBLIC_URLS = { "/api/user/login", "/api/user/register", "/api/user/reset/password/**", "/api/user/image/**"};
	//public static final String[] PUBLIC_URLS = { "**"};
}
