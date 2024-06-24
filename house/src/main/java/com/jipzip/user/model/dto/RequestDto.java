package com.jipzip.user.model.dto;

public class RequestDto {

	private UserDto user;
    private UserInfoDto userInfo;
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public UserInfoDto getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfoDto userInfo) {
		this.userInfo = userInfo;
	}
	@Override
	public String toString() {
		return "RequestDto [user=" + user + ", userInfo=" + userInfo + "]";
	}
    
}
