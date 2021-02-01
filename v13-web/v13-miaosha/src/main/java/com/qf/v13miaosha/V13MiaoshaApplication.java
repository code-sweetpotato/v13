package com.qf.v13miaosha;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.qf.v13miaosha.mapper")
@EnableScheduling
public class V13MiaoshaApplication {

	public static void main(String[] args) {
		SpringApplication.run(V13MiaoshaApplication.class, args);

	}

}
