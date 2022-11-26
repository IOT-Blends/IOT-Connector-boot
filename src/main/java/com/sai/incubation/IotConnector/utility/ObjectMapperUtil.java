package com.sai.incubation.IotConnector.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sai.incubation.IotConnector.domain.EntityDocument.User;
import com.sai.incubation.IotConnector.mixin.UserMixin;

public class ObjectMapperUtil {

	public static void addJacksonMixins(ObjectMapper objectMapper) {
		objectMapper.addMixIn(User.class, UserMixin.class);
	}
}
