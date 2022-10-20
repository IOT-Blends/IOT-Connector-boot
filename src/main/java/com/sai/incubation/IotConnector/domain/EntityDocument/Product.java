package com.sai.incubation.IotConnector.domain.EntityDocument;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "product")
public class Product {

	@Id
	private String id;
	@Indexed(unique = true)
	private String productId;
	private String productType;
	@Field(name = "productDesc")
	private String productDescription;
	private String productPrice;
	private String productImage;

	/* private Map<String, Object> properties; */

}
