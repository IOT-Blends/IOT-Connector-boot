package com.sai.incubation.IotConnector.domain.EntityDocument;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "master_product")
public class MasterProduct {
	
	@Transient
    public static final String SEQUENCE_NAME = "master_product_sequence";

	@Id
	private long id;
	@Indexed(unique=true)
	private String productName;
}
