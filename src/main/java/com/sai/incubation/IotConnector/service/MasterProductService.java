package com.sai.incubation.IotConnector.service;

import java.util.List;
import java.util.Optional;

import com.sai.incubation.IotConnector.domain.EntityDocument.MasterProduct;

public interface MasterProductService {

	public Optional<MasterProduct> addMasterProduct(MasterProduct product) throws Exception;
	
	public Optional<MasterProduct> updateProduct(MasterProduct product) throws Exception;
	
	public Optional<List<MasterProduct>> getAllProducts() throws Exception;
	
	public Optional<String> deleteProduct(long id) throws Exception;
}
