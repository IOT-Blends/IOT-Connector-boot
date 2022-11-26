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

import com.sai.incubation.IotConnector.domain.Common.HttpResponseObj;
import com.sai.incubation.IotConnector.domain.EntityDocument.MasterProduct;
import com.sai.incubation.IotConnector.service.MasterProductService;
import com.sai.incubation.IotConnector.utility.CommonWebUtil;

@RestController
@RequestMapping("api/master/product")
public class MasterProductController {

	private Log log = LogFactory.getLog(MasterProductController.class);
	private static final String MASTER_PRODUCT_NOT_FOUND = "Master Product Not Found";
	private static final String MASTER_PRODUCT_FOUND = "Master Product Already Found";
	
	@Autowired
	MasterProductService productService;
	
	/*@PostMapping("/add")
	public ResponseEntity<HttpResponse> addProduct(@RequestBody MasterProduct product) {
		Optional<MasterProduct> productOpt = Optional.empty();

		try {
			productOpt = productService.addMasterProduct(product);
		} catch (Exception e) {
			log.error("Error while Creating a Product");
		}

		return productOpt.isPresent()
				? CommonWebUtility.createResponseEntity(HttpStatus.OK, null, null, productOpt.get())
				: CommonWebUtility.createResponseEntity(HttpStatus.FOUND, null, MASTER_PRODUCT_FOUND, null);
	}*/
	
	/*@PutMapping("/update")
	public ResponseEntity<HttpResponse> updateProduct(@RequestBody MasterProduct product) throws Exception {
		Optional<MasterProduct> updatedProduct = Optional.empty();
		try {
			updatedProduct = productService.updateProduct(product);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return updatedProduct != null ? CommonWebUtility.createResponseEntity(HttpStatus.OK, null, null, updatedProduct)
				: CommonWebUtility.createResponseEntity(HttpStatus.EXPECTATION_FAILED, null,
						"Error while updating the Master Product", null);
	}*/

	/*@GetMapping("/all")
	public ResponseEntity<HttpResponse> getAllProducts() {
		Optional<List<MasterProduct>> productOpt = Optional.empty();

		try {
			productOpt = productService.getAllProducts();
		} catch (Exception e) {
			log.error("Error while retrieving all Master Product");
		}

		return productOpt.isPresent()
				? CommonWebUtility.createResponseEntity(HttpStatus.OK, null, null, productOpt.get())
				: CommonWebUtility.createResponseEntity(HttpStatus.NOT_FOUND, null, MASTER_PRODUCT_NOT_FOUND, null);
	}*/
	
	/*@DeleteMapping("/delete/{id}")
	public ResponseEntity<HttpResponse> deleteProduct(@PathVariable long id) {

		Optional<String> response = Optional.empty();
		try {
			response = productService.deleteProduct(id);
		} catch (Exception e) {
			log.error("Error while retrieving all Master Product");
		}

		return response.isPresent() ? CommonWebUtility.createResponseEntity(HttpStatus.OK, null, null, response.get())
				: CommonWebUtility.createResponseEntity(HttpStatus.NOT_FOUND, null, MASTER_PRODUCT_NOT_FOUND, null);
	}*/
}
