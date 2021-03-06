package com.sai.incubation.IotConnector.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Service
public class LoginAttemptService {
	private static final int MAX_NO_OF_ATTEMPTS = 3;
	private static final int ATTEMPT_INCREMENT = 1;
	private LoadingCache<String, Integer> loginAttemptCache;
	
	public LoginAttemptService() {
		super();
		loginAttemptCache = CacheBuilder.newBuilder()
				.expireAfterWrite(15, TimeUnit.MINUTES).maximumSize(100)
				.build(new CacheLoader<String, Integer>() {
						public Integer load(String key) {
							return 0;
						}
				});
	}
	
	public void evictUserFromLoginAttemptCache(String username) {
		loginAttemptCache.invalidate(username);
	}
	
	public void addUserToLoginAttemptCache(String username) {
		int attempts = 0;
		try {
			attempts = ATTEMPT_INCREMENT + loginAttemptCache.get(username);
			loginAttemptCache.put(username, attempts);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	public boolean hasExceededMaxAttempts(String username) {
		try {
			return loginAttemptCache.get(username) >= MAX_NO_OF_ATTEMPTS;
		}
		catch(ExecutionException e) {
			e.printStackTrace();
		}
		return false;
	}

}
