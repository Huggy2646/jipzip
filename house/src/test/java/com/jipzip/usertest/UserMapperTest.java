package com.jipzip.usertest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jipzip.config.PwEncryptionUtil;
import com.jipzip.user.mapper.UserMapper;
import com.jipzip.user.model.dto.UserDto;
import com.jipzip.user.model.dto.UserInfoDto;

public class UserMapperTest extends AbstractTest {

	@Autowired
	private UserMapper usermapper;
	private PwEncryptionUtil pwencryption = PwEncryptionUtil.getInstance();
	
	@Test
//	@Ignore
	public void testLogin() {
		Map<String,Object> logininfo = new HashMap<>();
		
		logininfo.put("userdto",new UserDto("ssafy","310c4a1b75e6c6aeea48b585de0d1c","-106 -73 -85 -48 -1 -4 2 75 -77 83 -127 79 -59 -116 -46 -85"));
		logininfo.put("pw","310c4a1b75e6c6aeea48b585de0d1c");
		
		UserDto result = usermapper.login(logininfo);
//		System.out.println(result.getId());
		assertEquals("ssafy",result.getId());
	}
	
	
	@Test
	@Ignore
	public void testInsertuser() {
	
		int result =  usermapper.writeuser(new UserDto("1","1","1"));
		assertEquals(1,result );
	}
	
	@Test
	@Ignore
	public void testInsertuserinfo() {	
		int result =  usermapper.writeuserinfo(new UserInfoDto("1","1","1","1","1","1"));
		assertEquals(1,result );
	}
	
	@Test
	@Ignore
	public void testgetUserInfo() {
		UserInfoDto result = usermapper.getUserInfo("ssafy");
		assertEquals("김싸피",result.getName());
	}
	
	@Test
	@Ignore
	public void testModifyuserinfo() {
		int result;
		//result= usermapper.modifyUserInfo(new UserInfoDto("admin","admin","관리자","부울경시 싸피동 11구","ssafy@ssafy.com","test"));
		result = usermapper.modifyUserInfo(new UserInfoDto("admin","admin","관리자","부울경시 싸피동 11구","ssafy@ssafy.com","010-0456-7890"));
		assertEquals(1,result);
	}
}
