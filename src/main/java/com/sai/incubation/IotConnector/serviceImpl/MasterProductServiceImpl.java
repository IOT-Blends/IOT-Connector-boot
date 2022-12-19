package com.sai.incubation.IotConnector.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sai.incubation.IotConnector.domain.EntityDocument.MasterProduct;
import com.sai.incubation.IotConnector.repository.MasterProductRepository;
import com.sai.incubation.IotConnector.service.MasterProductService;
import com.sai.incubation.IotConnector.service.SequenceGeneratorService;

@Service
public class MasterProductServiceImpl implements MasterProductService{

	@Autowired
	MasterProductRepository productRepo;
	
	@Autowired
	SequenceGeneratorService sequenceGeneratorService;
	
	@Override
	public Optional<MasterProduct> addMasterProduct(MasterProduct product) throws Exception {
		product.setId(sequenceGeneratorService.generateSequence(MasterProduct.SEQUENCE_NAME));
		MasterProduct savedProduct = productRepo.save(product);

		return savedProduct != null ? Optional.of(savedProduct) : Optional.empty();
	}

	@Override
	public Optional<MasterProduct> updateProduct(MasterProduct product) throws Exception {
		Optional<MasterProduct> existingProduct = Optional.empty();
		MasterProduct updatedProduct = null;
		
		if (!StringUtils.isEmpty(product.getId())) {
			existingProduct = this.getProductById(product.getId());
		}

		if (existingProduct.isPresent()) {
			product.setId(existingProduct.get().getId());
			updatedProduct = productRepo.save(product);
		}
		
		return updatedProduct != null ? Optional.of(updatedProduct) : Optional.empty();
	}

	@Override
	public Optional<List<MasterProduct>> getAllProducts() throws Exception {
		List<MasterProduct> productList = productRepo.findAll();
		return productList.isEmpty() ? Optional.empty() : Optional.of(productList);
	}

	@Override
	public Optional<String> deleteProduct(long id) throws Exception {
		productRepo.deleteById(id);

		return Optional.of("Master Product ID : " + id + "has been deleted successfully");
	}
	
	public Optional<MasterProduct> getProductById(long id) throws Exception {
		Optional<MasterProduct> productObj = productRepo.findById(id);
		return productObj != null ? productObj : Optional.empty();
	}
}
