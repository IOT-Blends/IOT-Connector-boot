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

import com.sai.incubation.IotConnector.domain.Common.CommonResponseEntity;
import com.sai.incubation.IotConnector.domain.EntityDocument.Product;
import com.sai.incubation.IotConnector.domain.responseentity.ProductResponseEntity;
import com.sai.incubation.IotConnector.service.ProductService;
import com.sai.incubation.IotConnector.utility.CommonWebUtil;

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

	@PostMapping("/add")
	public ResponseEntity<ProductResponseEntity> addProduct(@RequestBody Product product) {
		Optional<Product> productOpt = Optional.empty();

		try {
			productOpt = productService.createProduct(product);
		} catch (Exception e) {
			log.error("Error while Creating a Product");
		}

		return productOpt.isPresent()
				? CommonWebUtil.createProductHttpResponseEntity(HttpStatus.OK, null, null, productOpt.get())
				: CommonWebUtil.createProductHttpResponseEntity(HttpStatus.FOUND, null, "Product Already Found", null);
	}

	/*@PutMapping("/update")
	public ResponseEntity<HttpResponse> updateProduct(@RequestBody Product product) throws Exception {
		return productService.updateProduct(product);
	}*/

	@GetMapping("/all")
	public ResponseEntity<ProductResponseEntity> getAllProducts() {
		Optional<List<Product>> productOpt = Optional.empty();

		try {
			productOpt = productService.getAllProducts();
		} catch (Exception e) {
			log.error("Error while retrieving all Product");
		}

		return productOpt.isPresent()
				? CommonWebUtil.createProductHttpResponseEntity(HttpStatus.OK, null, null, productOpt.get())
				: CommonWebUtil.createProductHttpResponseEntity(HttpStatus.NOT_FOUND, null, "Product Not Found", null);
	}

	@GetMapping("/getById/{id}")
	public ResponseEntity<ProductResponseEntity> getProductById(@PathVariable String id) {
		Optional<Product> productOpt = Optional.empty();

		try {
			productOpt = productService.getProductById(id);
		} catch (Exception e) {
			log.error("Error while retrieving all Product");
		}

		return productOpt.isPresent()
				? CommonWebUtil.createProductHttpResponseEntity(HttpStatus.OK, null, null, productOpt.get())
				: CommonWebUtil.createProductHttpResponseEntity(HttpStatus.NOT_FOUND, null, "Product Not Found", null);
	}

	/*@DeleteMapping("/delete/{id}")
	public ResponseEntity<HttpResponse> deleteProduct(@PathVariable String id) {

		Optional<String> response = Optional.empty();
		try {
			response = productService.deleteProduct(id);
		} catch (Exception e) {
			log.error("Error while retrieving all Product");
		}

		return response.isPresent() ? CommonWebUtility.createResponseEntity(HttpStatus.OK, null, null, response.get())
				: CommonWebUtility.createResponseEntity(HttpStatus.NOT_FOUND, null, "Product Not Found", null);
	}*/

}
