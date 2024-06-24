package com.jipzip.user.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.jipzip.config.PwEncryptionUtil;
import com.jipzip.user.mapper.UserMapper;
import com.jipzip.user.model.dto.UserDto;
import com.jipzip.user.model.dto.UserInfoDto;

@Service
public class UserServiceImpl implements UserService {

	private UserMapper usermapper;
	
	public UserServiceImpl(UserMapper usermapper) {
		this.usermapper = usermapper;
	}

	@Override
	public UserDto login(UserDto user,String password) {
		Map<String,Object> logininfo = new HashMap<>();
		logininfo.put("userdto",user);
		logininfo.put("pw",password);
		return usermapper.login(logininfo);
	}

	@Override
	public UserDto getUser(String userId) {
		UserDto result = usermapper.getUser(userId);
		return result;
}

	@Override
	public String getKey(String userId) {
		
		return usermapper.getKey(userId);
	}

	@Override
	public UserInfoDto getUserInfo(String userId) {
		// TODO Auto-generated method stub
		return usermapper.getUserInfo(userId);
	}
	
	@Transactional
	@Override
	public int join(UserDto user, UserInfoDto userInfo) {
		// 비밀번호 해싱
		PwEncryptionUtil Einstance = PwEncryptionUtil.getInstance();
		String [] hashing_info=Einstance.givenRawPasswordAndSalt_whenArgon2AlgorithmIsUsed_thenHashIsCorrect(user.getPw(),null);
		String hashedPw = hashing_info[0]; // 해싱된 비밀번호
		String key = hashing_info[1]; // 해싱 키

		// 해싱된 비밀번호로 변경
		user.setPw(hashedPw);
		// 해싱 키 저장
		user.setPkey(key);
		
		// 회원가입
		int userRes = usermapper.writeuser(user);
		int userinfoRes = usermapper.writeuserinfo(userInfo);
		return (userRes == 1 && userinfoRes == 1) ? 1 : 0;
	}

	@Override
	public int modifyUserInfo(UserInfoDto modifyUser) {
		return usermapper.modifyUserInfo(modifyUser);
	}

	@Transactional
	@Override
	public int remove(String userId) {
		int user = usermapper.removeUser(userId);
		int userinfo = usermapper.removeUserInfo(userId);
		return 1;
	}

	@Override
	public int modifyPw(UserDto loginResult) {
		return usermapper.modifyPw(loginResult);
	}

	@Override
	public int setTempPw(UserDto temp) {
		System.out.println(temp);
		return usermapper.modifyPw(temp);
	}




}
