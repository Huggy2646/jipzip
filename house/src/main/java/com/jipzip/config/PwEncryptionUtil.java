package com.jipzip.config;

import java.beans.JavaBean;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;

import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;
import org.springframework.stereotype.Component;
@Component
public class PwEncryptionUtil {
	
	//singleton
	private static PwEncryptionUtil instacne = new PwEncryptionUtil();
	
	private PwEncryptionUtil() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static PwEncryptionUtil getInstance() {
		return instacne;
	}

	
	
	//argon
	//make key
	private byte[] generateSalt16Byte() {
		SecureRandom secureRandom = new SecureRandom();
		byte[] salt = new byte[16];
		secureRandom.nextBytes(salt);
		return salt;
	}
	//encryption
	public String[] givenRawPasswordAndSalt_whenArgon2AlgorithmIsUsed_thenHashIsCorrect(String pw,String key) {
		byte[]salt= new byte[16];
		//key ==null => join일 때 
		if(key==null) {
			salt = generateSalt16Byte();
			StringBuilder sb = new StringBuilder();
			for(byte b: salt) {
				sb.append(b).append(" ");
			}
			key = sb.toString();
			
			
		}	
		//키 값이 있을 때(로그인 할때)
		else {
			// String형태의 키를 byte arr로 변경
			String[] bytes=key.split(" ");

			for(int i=0; i<16; i++) {
				salt[i]=Byte.parseByte(bytes[i]);
			}
			
			
		}
			

		
		String password = pw;

		StringBuilder tohex = new StringBuilder();

		
		//반복 횟수, 메모리 제한, 해시 길이, 병렬 처리 인자, 솔트 등 알고리즘의 필수 매개변수를 정의
		int iterations = 2;
		int memLimit = 66536;
		int hashLength = 32;
		int parallelism = 1;

		Argon2Parameters.Builder builder = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
				.withVersion(Argon2Parameters.ARGON2_VERSION_13)
				.withIterations(iterations)
				.withMemoryAsKB(memLimit)
				.withParallelism(parallelism)
				.withSalt(salt);
		
		
		//객체 생성
		Argon2BytesGenerator generate = new Argon2BytesGenerator();
		generate.init(builder.build());
		byte[] result = new byte[hashLength];
		generate.generateBytes(password.getBytes(StandardCharsets.UTF_8), result, 0, result.length);
		
		//해싱한 것 16진수로 변경 후 문자열화
		for(byte b:result)
			tohex.append(String.format("%02x",b));
			
		password = tohex.toString();
		
		// db에 설정된 길이 만큼 sub
		if(29<password.length()) {
			password=password.substring(0,30);
		}
		
		//db에 저장 할 key도 같이 return
		return new String[] {password,key};
	}	
}
