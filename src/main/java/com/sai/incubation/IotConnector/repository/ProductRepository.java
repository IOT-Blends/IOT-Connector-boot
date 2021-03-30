package com.sai.incubation.IotConnector.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.sai.incubation.IotConnector.domain.EntityDocument.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

	Product findByProductId(String productId);
	
	void deleteByProductId(String productId);
	
	
}
