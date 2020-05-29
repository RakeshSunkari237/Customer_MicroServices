package com.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class CustomerMicroServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerMicroServicesApplication.class, args);
	}
	
	@Bean
	public Docket docket() {
		ApiInfo info=new ApiInfoBuilder()
				.title("Customer MicroService Rest Documentation")
				.description("Customer Rest End Points for Register , Login and CustomerProfile ")
				.contact(new Contact("rakesh", null, "rakeshsunkari95@gmail.com"))
				.license("sathyatech").licenseUrl("http://www.sathyatech.com")
				.build();
		
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.customer.controller"))
				.paths(PathSelectors.any())
				.build().apiInfo(info);
	}
	
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
