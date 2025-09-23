package com.spark.dating.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI().info(info);
	}
	
	private Info info = new Info().title("Spark API Test").version("0.0.1").description("<h3>Project Swagger Test</h3>"); 
	
}
