package com.jipzip.usertest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.jipzip.mail.controller.MailRestController;
import com.jipzip.user.model.dto.UserDto;

import jakarta.mail.MessagingException;

public class findPwTest extends AbstractTest  {

	@Autowired
	MailRestController mrc;

	@Test
	public void findPwTest() {
		
		try {
			ResponseEntity<?> result = mrc.findPw(new UserDto("ssafy",null,null) );
			System.out.println(result);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
