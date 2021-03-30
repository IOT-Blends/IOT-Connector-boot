package com.sai.incubation.IotConnector.controller;

import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sai.incubation.IotConnector.domain.Common.HttpResponse;
import com.sai.incubation.IotConnector.domain.EntityDocument.Product;
import com.sai.incubation.IotConnector.service.ProductService;
import com.sai.incubation.IotConnector.utility.CommonWebUtility;

@RestController
@RequestMapping("api/product")
public class ProductController {

	private Log log = LogFactory.getLog(ProductController.class);

	@Autowired
	ProductService productService;

	@GetMapping("/home")
	public String welcome() {
		return "Welcome to IOT Connector - Nishanth";
	}

	@PostMapping("/create")
	public ResponseEntity<HttpResponse> createProduct(@RequestBody Product product) {
		Optional<Product> productOpt = Optional.empty();

		try {
			productOpt = productService.createProduct(product);
		} catch (Exception e) {
			log.error("Error while Creating a Product");
		}

		return productOpt.isPresent()
				? CommonWebUtility.createResponseEntiry(HttpStatus.OK, null, null, productOpt.get())
				: CommonWebUtility.createResponseEntiry(HttpStatus.NOT_FOUND, null, "Product Not Found", null);
	}

	@PutMapping("update")
	public ResponseEntity<HttpResponse> updateProduct(@RequestBody Product product) throws Exception {
		return productService.updateProduct(product);
	}

	@GetMapping("/all")
	public ResponseEntity<HttpResponse> getAllProducts() {
		Optional<List<Product>> productOpt = Optional.empty();

		try {
			productOpt = productService.getAllProducts();
		} catch (Exception e) {
			log.error("Error while retrieving all Product");
		}

		return productOpt.isPresent()
				? CommonWebUtility.createResponseEntiry(HttpStatus.OK, null, null, productOpt.get())
				: CommonWebUtility.createResponseEntiry(HttpStatus.NOT_FOUND, null, "Product Not Found", null);
	}

	@GetMapping("/getById/{id}")
	public ResponseEntity<HttpResponse> getProductById(@PathVariable String id) {
		Optional<Product> productOpt = Optional.empty();

		try {
			productOpt = productService.getProductById(id);
		} catch (Exception e) {
			log.error("Error while retrieving all Product");
		}

		return productOpt.isPresent()
				? CommonWebUtility.createResponseEntiry(HttpStatus.OK, null, null, productOpt.get())
				: CommonWebUtility.createResponseEntiry(HttpStatus.NOT_FOUND, null, "Product Not Found", null);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<HttpResponse> deleteProduct(@PathVariable String id) {

		Optional<String> response = Optional.empty();
		try {
			response = productService.deleteProduct(id);
		} catch (Exception e) {
			log.error("Error while retrieving all Product");
		}

		return response.isPresent() ? CommonWebUtility.createResponseEntiry(HttpStatus.OK, null, null, response.get())
				: CommonWebUtility.createResponseEntiry(HttpStatus.NOT_FOUND, null, "Product Not Found", null);
	}

}
