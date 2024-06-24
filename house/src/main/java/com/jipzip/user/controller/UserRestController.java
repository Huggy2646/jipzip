package com.jipzip.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jipzip.user.model.dto.ModifyPw;
import com.jipzip.user.model.dto.RequestDto;
import com.jipzip.config.PwEncryptionUtil;
import com.jipzip.user.model.dto.UserDto;
import com.jipzip.user.model.dto.UserInfoDto;
import com.jipzip.user.model.service.UserService;

@RestController
@RequestMapping("/userapi")
public class UserRestController {

	private UserService userService;
	private PwEncryptionUtil pwencryption;
	public UserRestController(UserService userService, PwEncryptionUtil pwencryption) {
		super();
		this.userService = userService;
		this.pwencryption = pwencryption;
	}
	

	// 로그인
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserDto user){
		try {
			System.out.println(user.getId());
			// 입력 받은 id에 저장된 key 받기
			String key = userService.getKey(user.getId());
			if(key==null) {
//				throw new Exception("incorrect");
				return ResponseEntity.ok(null);
			}
			// 입력 받은 id에 저장된 비밀번호 받기
			String pw = userService.getUser(user.getId()).getPw();
			// 얻어온 비밀번호와,key를 활용하여 암호화된 pw 받기
			String password = pwencryption.givenRawPasswordAndSalt_whenArgon2AlgorithmIsUsed_thenHashIsCorrect(user.getPw(),key)[0];
			// 입력 받은 pw와 암호화된 pw가 같으면
			if(pw.equals(password)) {
				UserDto result = userService.login(user,password);
				return ResponseEntity.ok(result);
			}
			else {				
//				throw new Exception("incorrect");
				return ResponseEntity.ok(null);
			}
						
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// id로 유저 정보 조회
	@GetMapping("/users/{userId}")
	public ResponseEntity<?> getUser(@PathVariable String userId){
		try {
		
			UserDto result = userService.getUser(userId);
			
			return ResponseEntity.ok(result);			
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// id로 유저 가입 정보 조회
	@GetMapping("/infos/{userId}")
	public ResponseEntity<?> getUserInfo(@PathVariable String userId){
		try {
			UserInfoDto result = userService.getUserInfo(userId);
			return ResponseEntity.ok(result);			
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	
	// 회원가입
	@PostMapping("/users")
	public ResponseEntity<?>join(@RequestBody RequestDto requestDto){
		try {
			UserDto user = requestDto.getUser();
			UserInfoDto userInfo = requestDto.getUserInfo();
			System.out.println("user:" + user);
			System.out.println("userinfo: " + userInfo);
			int result = userService.join(user, userInfo);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	

	// 회원 정보 수정
	@PutMapping("/users")
	public ResponseEntity<?>modifyUserInfo(@RequestBody UserInfoDto modifyUser){
		try {
			int result = userService.modifyUserInfo(modifyUser);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	//비밀번호 변경
	@PutMapping("/modifypw")
	public ResponseEntity<?>modifyPw(@RequestBody ModifyPw modifyPw){
//		System.out.println(modifyPw);
		try {
			UserDto user = new UserDto(modifyPw.getId(),modifyPw.getPw(),null);
			UserDto loginResult = (UserDto)login(user).getBody();
//			System.out.println(loginResult);
			if(loginResult==null) {
				System.out.println("잘못됨");
				return ResponseEntity.ok(null);
			}
			else {
				String[] newPasswordnKey = pwencryption.givenRawPasswordAndSalt_whenArgon2AlgorithmIsUsed_thenHashIsCorrect(modifyPw.getModifypw(),null);
				String pw = newPasswordnKey[0];
				String key = newPasswordnKey[1];
				loginResult.setPw(pw);
				loginResult.setPkey(key);
				int result = userService.modifyPw(loginResult);
				return ResponseEntity.ok(result);
			}
			
		}
		catch(Exception e){
			return exceptionHandling(e);
		}
	} 
	
	// 회원 탈퇴
	@DeleteMapping("/users")
	public ResponseEntity<?> delete(String id) {
		int res = 0;
		try {
			res = userService.remove(id);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
		return ResponseEntity.ok(res);
	}

	// 에러 핸들링
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return ResponseEntity
				.internalServerError()
				.body("Sorry: " + e.getMessage());
	}
}
