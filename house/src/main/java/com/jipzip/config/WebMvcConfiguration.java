package com.jipzip.config;
import java.util.Arrays;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")  // 모든 요청 주소를 허용
			.allowedOrigins("http://localhost:8080", 
					"http://localhost:5173", 
					"http://192.168.204.100:5173",
					"http://192.168.204.113:5173",
					"http://192.168.204.114:5173")  // 화이트리스트 (요청을 허용할 서버 주소)
			.allowedMethods(  // 허용 할 HTTP Method
					HttpMethod.GET.name(),
					HttpMethod.POST.name(),
					HttpMethod.PUT.name(),
					HttpMethod.PATCH.name(),  // 부분 수정 요청 시
					HttpMethod.DELETE.name(),
					HttpMethod.HEAD.name(),  // preflight
					HttpMethod.OPTIONS.name())  // preflight
			.maxAge(1800);  // 1800초 동안 preflight 결과를 캐시에 저장
	}
}
