package com.sai.incubation.IotConnector.domain.EntityDocument;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "product")
public class Product {

	@Id
	private String id;

	private String productId;

	private String productName;

	@Field(name = "productDesc")
	private String productDescription;

	private String productPrice;

	private String productImage;

	/* private Map<String, Object> properties; */

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	/*
	 * public Map<String, Object> getProperties() { return properties; }
	 * 
	 * public void setProperties(Map<String, Object> properties) { this.properties =
	 * properties; }
	 */

}
