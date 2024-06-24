package com.jipzip.user.mapper;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.jipzip.user.model.dto.UserDto;
import com.jipzip.user.model.dto.UserInfoDto;

@Mapper
public interface UserMapper {
	
	
	UserDto login(Map<String, Object> logininfo);
	
	UserDto getUser(String userId);
	
	String getKey(String userId);
	
	UserInfoDto getUserInfo(String userId);
	
	int writeuser(UserDto user);
	int writeuserinfo(UserInfoDto userinfo);
	
	int modifyUserInfo(UserInfoDto modifyUser);
	
	// 비밀번호변경 다시 해야됨
	//int modifyPwd(UserDto modifyPwdUser);
	
	int removeUser(String userId);
	
	int removeUserInfo(String userId);

	int modifyPw(UserDto loginResult);

	int setTempPw(UserDto temp);
	
	
}
