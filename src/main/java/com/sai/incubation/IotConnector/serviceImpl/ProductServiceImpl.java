package com.sai.incubation.IotConnector.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sai.incubation.IotConnector.constants.ExceptionConstants;
import com.sai.incubation.IotConnector.domain.EntityDocument.Product;
import com.sai.incubation.IotConnector.domain.responseentity.ProductResponseEntity;
import com.sai.incubation.IotConnector.repository.ProductRepository;
import com.sai.incubation.IotConnector.service.ProductService;
import com.sai.incubation.IotConnector.utility.CommonWebUtil;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepo;

	@Override
	public Optional<Product> createProduct(Product product) throws Exception {

		Product savedProduct = productRepo.save(product);

		return savedProduct != null ? Optional.of(savedProduct) : Optional.empty();
	}

	// Revisit the following method implementation and re-factor
	@Override
	public ResponseEntity<ProductResponseEntity> updateProduct(Product newProduct) throws Exception {
		Optional<Product> existingProduct = Optional.empty();
		Product updatedProduct = null;
		try {
			if (StringUtils.isEmpty(newProduct.getProductId())) {
				/*return CommonWebUtility.incorrectRequestData(HttpStatus.BAD_REQUEST,
						ExceptionConstants.PRODUCTID_NOTFOUND);*/
			} else {
				existingProduct = this.getProductById(newProduct.getProductId());
			}

			if (!existingProduct.isPresent()) {
				//return CommonWebUtility.incorrectRequestData(HttpStatus.NOT_FOUND, ExceptionConstants.PRODUCT_NOTFOUND);
			}
			newProduct.setProductId(existingProduct.get().getProductId());
			updatedProduct = productRepo.save(newProduct);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return updatedProduct != null ? CommonWebUtil.createProductHttpResponseEntity(HttpStatus.OK, null, null, updatedProduct)
				: CommonWebUtil.createProductHttpResponseEntity(HttpStatus.EXPECTATION_FAILED, null,
						"Error while updating the Product", null);
	}

	@Override
	public Optional<List<Product>> getAllProducts() throws Exception {

		List<Product> productList = productRepo.findAll();
		return productList.isEmpty() ? Optional.empty() : Optional.of(productList);
	}

	@Override
	public Optional<Product> getProductById(String id) throws Exception {

		Product productObj = productRepo.findByProductId(id);
		return productObj != null ? Optional.of(productObj) : Optional.empty();
	}

	@Override
	public Optional<String> deleteProduct(String id) throws Exception {

		productRepo.deleteByProductId(id);

		return Optional.of("Product ID : " + id + "has been deleted successfully");
	}

}
