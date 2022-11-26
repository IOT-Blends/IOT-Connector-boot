package com.sai.incubation.IotConnector.domain.responseentity;

import java.util.List;

import com.sai.incubation.IotConnector.domain.Common.HttpResponseEntity;
import com.sai.incubation.IotConnector.domain.EntityDocument.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseEntity extends HttpResponseEntity{

	private List<User> user;
}
