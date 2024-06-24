package com.jipzip.building.model.dto;

public class SearchCondition {
	
	private String sido;
	private String gugun;
	private String dong;
	private int year;
	private int month;
	// 정렬 기준
	private String sorter;
	// 정렬 순서(기본값: 오름차순)
	private String order = "asc";
	
	// 생성자들 --------------
	public SearchCondition() {} // 조건이 없으면 모든 매물 검색
	
	public SearchCondition(String sido, String gugun, String dong, int year, int month, String sorter, String order) {
		this.sido = sido;
		this.gugun = gugun;
		this.dong = dong;
		this.year = year;
		this.month = month;
		this.sorter = sorter;
		this.order = order;
	}
	
//	public SearchCondition(String sido, String gugun, String dong, int year, int month, String sorter) {
//		this(sido, gugun, dong, year, month, sorter, "asc");
//	}
//	
//	public SearchCondition(String sido, String gugun, String dong, int year, int month) {
//		this(sido, gugun, dong, year, month, null);
//	}
//	
	// 생성자들 끝 --------------

	public String getSido() {
		return sido;
	}

	public void setSido(String sido) {
		this.sido = sido;
	}

	public String getGugun() {
		return gugun;
	}

	public void setGugun(String gugun) {
		this.gugun = gugun;
	}

	public String getDong() {
		return dong;
	}

	public void setDong(String dong) {
		this.dong = dong;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public String getSorter() {
		return sorter;
	}

	public void setSorter(String sorter) {
		this.sorter = sorter;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "SearchCondition [sido=" + sido + ", gugun=" + gugun + ", dong=" + dong + ", year=" + year + ", month="
				+ month + ", sorter=" + sorter + ", order=" + order + "]";
	}
	
}
