package com.jc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@MapperScan(basePackages = "com.jc.mapper")
public class JcApplyApplication {

	public static void main(String[] args) {
		SpringApplication.run(JcApplyApplication.class, args);
	}
}
