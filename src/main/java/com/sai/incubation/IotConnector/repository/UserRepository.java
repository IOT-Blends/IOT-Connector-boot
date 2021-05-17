package com.sai.incubation.IotConnector.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sai.incubation.IotConnector.domain.EntityDocument.User;

public interface UserRepository extends MongoRepository<User, String> {

	User findByEmail(String email);

	void deleteByEmail(User user);

}
