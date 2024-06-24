package com.jipzip.mail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jipzip.config.PwEncryptionUtil;
import com.jipzip.mail.MailManager;
import com.jipzip.user.model.dto.UserDto;
import com.jipzip.user.model.dto.UserInfoDto;
import com.jipzip.user.model.service.UserService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/mail")
public class MailRestController {

	@Autowired
	UserService us;
	@Autowired
	MailManager mm;
	
	
	PwEncryptionUtil pwencryption;
	public MailRestController(PwEncryptionUtil pwencryption) {
		super();
		this.pwencryption = pwencryption;
	}	
	
	
	@PutMapping("/findpw")
	public ResponseEntity<?> findPw(@RequestBody UserDto input) throws MessagingException{
		System.out.println(input.getId());
		// 유저 로그인 정보 얻기
		UserDto user = us.getUser(input.getId());
		System.out.println(user);
		// 유저 정보 얻기
		UserInfoDto userinfo = us.getUserInfo(input.getId());
		if(user==null) {
			return ResponseEntity.ok(null);
		}
		
		System.out.println(user);
		System.out.println(userinfo);
		String pw = user.getPw();
		String email = userinfo.getEmail();
		StringBuilder newPw = new StringBuilder();
		for(int i=0; i<pw.length(); i++) {
			if(i%2==0) {
				newPw.append(pw.charAt(i));
			}
		}
		
		String[] temppw_key = pwencryption.givenRawPasswordAndSalt_whenArgon2AlgorithmIsUsed_thenHashIsCorrect(newPw.toString(),null);
		
		int result = us.setTempPw(new UserDto(input.getId(),temppw_key[0],temppw_key[1]));
		System.out.println(newPw);
		String sub=  "임시 비밀번호가 발송되었습니다.";
		String con= "임시 비밀번호 : "+ newPw;
		
		mm.send(email,sub,con);
		
		return ResponseEntity.ok(result);
		
	}
}
