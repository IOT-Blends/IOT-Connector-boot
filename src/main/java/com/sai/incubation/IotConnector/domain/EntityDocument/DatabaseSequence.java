package com.sai.incubation.IotConnector.domain.EntityDocument;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "database_sequence")
public class DatabaseSequence {

	@Id
    private String id;

    private long seq;
}
