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
	private String areaCode;
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
	
	/*public User(String id, String firstName, String lastName, String email, String password, String areaCode,
			String mobileNumber, String dob, String gender, String addressLine1, String addressLine2, String city,
			String state, String zip, String country, String creator, String role, String[] authority,
			Date lastLoginDateDisplay, Date lastLoginDate, Boolean active, Boolean notLocked, 
			String profileImageUrl, MultipartFile profileImage) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.areaCode = areaCode;
		this.mobileNumber = mobileNumber;
		this.dob = dob;
		this.gender = gender;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.country = country;
		this.creator = creator;
		this.role = role;
		this.authority = authority;
		this.lastLoginDateDisplay = lastLoginDateDisplay;
		this.lastLoginDate = lastLoginDate;
		this.active = active;
		this.notLocked = notLocked;
		this.profileImageUrl = profileImageUrl;
		this.profileImage = profileImage;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String[] getAuthority() {
		return authority;
	}

	public void setAuthority(String[] authority) {
		this.authority = authority;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Date getLastLoginDateDisplay() {
		return lastLoginDateDisplay;
	}

	public void setLastLoginDateDisplay(Date lastLoginDateDisplay) {
		this.lastLoginDateDisplay = lastLoginDateDisplay;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getNotLocked() {
		return notLocked;
	}

	public void setNotLocked(Boolean notLocked) {
		this.notLocked = notLocked;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public MultipartFile getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(MultipartFile profileImage) {
		this.profileImage = profileImage;
	}*/
}
