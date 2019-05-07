package com.isinonet.wdview;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan("com.isinonet.wdview.Mapper")
public class WdviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(WdviewApplication.class, args);
	}



}
