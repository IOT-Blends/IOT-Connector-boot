package com.sai.incubation.IotConnector.domain.EntityDocument;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
public class User {

	@Id
	private String userId;
	private String firstName;
	private String lastName;
	@Indexed(unique = true)
	private String email;
	private String password;
	private String phoneNumber;
	private Date dob;
	private String gender;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String zip;
	private String country;
	private String creator;
	private String role;
	private String[] authority;
	private Date lastLoginDateDisplay;
	private Date lastLoginDate;
	private Boolean active;
	private Boolean notLocked;
	private String profileImageUrl;
	private MultipartFile profileImage;
	
}
