package com.qf.v13emailservice;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class V13EmailServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(V13EmailServiceApplication.class, args);
	}

}
