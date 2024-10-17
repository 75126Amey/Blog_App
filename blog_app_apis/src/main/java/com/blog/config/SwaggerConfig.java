package com.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {
	 @Bean
	    public OpenAPI customOpenAPI() {
	        return new OpenAPI()
	            .info(new Info()
	                .title("Blogging Application : Backend")
	                .version("1.0")
	                .description("This project is developed by Amey Salunkhe")
	                .contact(new Contact()
	                    .name("Amey")
	                    .email("ameysalunkhe96@gmail.com"))
	                .license(new License()
	                    .name("License of APIs")
	                    .url("API license URL"))
	            );
	 }
}
