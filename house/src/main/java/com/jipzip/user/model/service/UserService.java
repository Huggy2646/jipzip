package com.jipzip.user.model.service;

import java.util.Map;

import com.jipzip.user.model.dto.UserDto;
import com.jipzip.user.model.dto.UserInfoDto;

public interface UserService {

	UserDto login(UserDto user,String password);

	UserDto getUser(String userId);

	String getKey(String userId);

	UserInfoDto getUserInfo(String userId);

	int join(UserDto user, UserInfoDto userInfo);

	int modifyUserInfo(UserInfoDto modifyUser);

	int remove(String userId);

	int modifyPw(UserDto loginResult);

	int setTempPw(UserDto tempPwDto);

}
