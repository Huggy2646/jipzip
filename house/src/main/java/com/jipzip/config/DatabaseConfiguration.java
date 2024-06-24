package com.jipzip.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource("classpath:/application.properties")
// application.properties에서 datasource 설정 시
// spring.datasource.hikari.jdbc-url로 하면 수동 설정
// spring.datasource.url로 하면 자동 설정(모든 Datasource의 url에 적용)
public class DatabaseConfiguration {

	@Bean // Bean으로 만들어라!!
	// PropertySource 경로에 있는 파일에서 prefix로 spring.datasource.hikari인 애들만 읽어라~
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	
	@Bean
	public DataSource dataSource() throws Exception {
		DataSource dataSource = new HikariDataSource(hikariConfig());
		return dataSource; // HikariCP에서 얻어오는 친구
	}
}
