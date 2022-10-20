package com.sai.incubation.IotConnector.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.sai.incubation.IotConnector.domain.EntityDocument.MasterProduct;

@Repository
public interface MasterProductRepository extends MongoRepository<MasterProduct, Long>{

}
