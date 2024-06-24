package com.jipzip.building.model.dto;

public class DetailCondition {

	String dongCode;
	String apartmentName;
	public DetailCondition(String dongCode, String apartmentName) {
		super();
		this.dongCode = dongCode;
		this.apartmentName = apartmentName;
	}
	public String getDongCode() {
		return dongCode;
	}
	public void setDongCode(String dongCode) {
		this.dongCode = dongCode;
	}
	public String getApartmentName() {
		return apartmentName;
	}
	public void setApartmentName(String apartmentName) {
		this.apartmentName = apartmentName;
	}
	@Override
	public String toString() {
		return "DetailCondition [dongCode=" + dongCode + ", apartmentName=" + apartmentName + "]";
	}
	
	
}
