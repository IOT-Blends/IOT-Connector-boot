package com.sai.incubation.IotConnector.domain.responseentity;

import com.sai.incubation.IotConnector.domain.Common.HttpResponseEntity;
import com.sai.incubation.IotConnector.domain.EntityDocument.Product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductResponseEntity extends HttpResponseEntity{

	private Product product;
}
