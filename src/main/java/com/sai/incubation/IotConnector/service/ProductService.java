package com.sai.incubation.IotConnector.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.sai.incubation.IotConnector.domain.EntityDocument.Product;
import com.sai.incubation.IotConnector.domain.responseentity.ProductResponseEntity;

public interface ProductService {

	public Optional<Product> createProduct(Product product) throws Exception;

	public ResponseEntity<ProductResponseEntity> updateProduct(Product product) throws Exception;

	public Optional<List<Product>> getAllProducts() throws Exception;

	public Optional<Product> getProductById(String id) throws Exception;

	public Optional<String> deleteProduct(String id) throws Exception;

}
