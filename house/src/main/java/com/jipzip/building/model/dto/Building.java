package com.jipzip.building.model.dto;

import java.math.BigInteger;

public class Building {
	
	private BigInteger aptCode; // 추가
	private String apartmentName;
	private String lng;
	private String lat;
	private String area;
	private String dealAmount;
	private String floor;
	private String dong;
	private String stat;
	
	public Building() {
		super();
	}
	public Building(String apartmentName, String lng, String lat, String area, String dealAmount, String floor,
			String dong, String stat) {
		super();
		this.apartmentName = apartmentName;
		this.lng = lng;
		this.lat = lat;
		this.area = area;
		this.dealAmount = dealAmount;
		this.floor = floor;
		this.dong = dong;
		this.stat = stat;
	}
	public String getApartmentName() {
		return apartmentName;
	}
	public void setApartmentName(String apartmentName) {
		this.apartmentName = apartmentName;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getDealAmount() {
		return dealAmount;
	}
	public void setDealAmount(String dealAmount) {
		this.dealAmount = dealAmount;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getDong() {
		return dong;
	}
	public void setDong(String dong) {
		this.dong = dong;
	}
	
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	public Building copy() { // 깊은 복사
		Building ret = new Building();
		ret.setApartmentName(this.getApartmentName());
		ret.setArea(this.getArea());
		ret.setDealAmount(this.getDealAmount());
		ret.setDong(this.getDong());
		ret.setFloor(this.getFloor());
		ret.setLat(this.getLat());
		ret.setLng(this.getLng());
		ret.setStat(this.getStat());
		return ret;
	}
	@Override
	public String toString() {
		return "House [apartmentName=" + apartmentName + ", lng=" + lng + ", lat=" + lat + ", area=" + area
				+ ", dealAmount=" + dealAmount + ", floor=" + floor + ", dong=" + dong + ", stat=" + stat + "]";
	}


	
	
}
