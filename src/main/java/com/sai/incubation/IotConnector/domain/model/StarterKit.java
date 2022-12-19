package com.sai.incubation.IotConnector.domain.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class StarterKit {
	
	@Id
	private long starterKitId;
	@Indexed(unique = true)
	private String productId;
	private String userId;
	private Boolean status;
	private Boolean powerStatus;

}
