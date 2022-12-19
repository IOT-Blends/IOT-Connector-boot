package com.sai.incubation.IotConnector.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class UserMixin {

	@JsonIgnore private String password;
}
