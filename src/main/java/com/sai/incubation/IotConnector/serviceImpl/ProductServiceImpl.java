package com.sai.incubation.IotConnector.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sai.incubation.IotConnector.constants.ExceptionConstants;
import com.sai.incubation.IotConnector.domain.Common.HttpResponse;
import com.sai.incubation.IotConnector.domain.EntityDocument.Product;
import com.sai.incubation.IotConnector.repository.ProductRepository;
import com.sai.incubation.IotConnector.service.ProductService;
import com.sai.incubation.IotConnector.utility.CommonWebUtility;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepo;

	@Override
	public Optional<Product> createProduct(Product product) throws Exception {

		Product saveedProduct = productRepo.save(product);

		return saveedProduct != null ? Optional.of(saveedProduct) : Optional.empty();
	}

	@Override
	public ResponseEntity<HttpResponse> updateProduct(Product newProduct) throws Exception {
		Optional<Product> existingProduct = Optional.empty();
		Product updatedProduct = null;
		try {
			if (StringUtils.isEmpty(newProduct.getProductId())) {
				return CommonWebUtility.incorrectRequestData(HttpStatus.BAD_REQUEST,
						ExceptionConstants.PRODUCTID_NOTFOUND);
			} else {
				existingProduct = this.getProductById(newProduct.getProductId());
			}

			if (!existingProduct.isPresent()) {
				return CommonWebUtility.incorrectRequestData(HttpStatus.NOT_FOUND, ExceptionConstants.PRODUCT_NOTFOUND);
			}
			newProduct.setId(existingProduct.get().getId());
			updatedProduct = productRepo.save(newProduct);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return updatedProduct != null ? CommonWebUtility.createResponseEntity(HttpStatus.OK, null, null, updatedProduct)
				: CommonWebUtility.createResponseEntity(HttpStatus.EXPECTATION_FAILED, null,
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

		return Optional.of("Product ID : " + id + "has been deleted successfullt");
	}

}
