package com.jipzip.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title="User(유저정보)", description="유저 정보를 가진 Domain Class")
public class UserDto {
	
	@Schema(description="아이디")
	String id;
	@Schema(description="패스워드")
	String pw;
	String pkey;
	@Schema(description="가입일자")
	String joindate;
	
	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public UserDto(String id, String pw, String pkey, String joindate) {
		super();
		this.id = id;
		this.pw = pw;
		this.pkey = pkey;
		this.joindate = joindate;
	}
	
	public UserDto(String id, String pw, String pkey) {
		super();
		this.id = id;
		this.pw = pw;
		this.pkey = pkey;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getPkey() {
		return pkey;
	}
	public void setPkey(String pkey) {
		this.pkey = pkey;
	}
	public String getJoindate() {
		return joindate;
	}
	public void setJoindate(String joindate) {
		this.joindate = joindate;
	}
	@Override
	public String toString() {
		return "UserDto [id=" + id + ", pw=" + pw + ", pkey=" + pkey + ", joindate=" + joindate + "]";
	}
	
	
}
