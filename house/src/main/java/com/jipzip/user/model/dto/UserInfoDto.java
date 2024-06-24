package com.jipzip.user.model.dto;


public class UserInfoDto {
	String id;
	String role;
	String name;
	String adress;
	String email;
	String mobileNumber;
	public UserInfoDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserInfoDto(String id, String role, String name, String adress, String email, String mobileNumber) {
		super();
		this.id = id;
		this.role = role;
		this.name = name;
		this.adress = adress;
		this.email = email;
		this.mobileNumber = mobileNumber;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile_number() {
		return mobileNumber;
	}
	public void setMobile_number(String mobile_number) {
		this.mobileNumber = mobile_number;
	}
	@Override
	public String toString() {
		return "UserInfoDto [id=" + id + ", role=" + role + ", name=" + name + ", adress=" + adress + ", email=" + email
				+ ", mobile_number=" + mobileNumber + "]";
	}
	
	
}
