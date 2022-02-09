package com.zensar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.modelmapper.ModelMapper;


import java.util.ArrayList;

@SpringBootApplication
@ComponentScan
public class MacysOrderMessageProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MacysOrderMessageProducerApplication.class, args);
	}

	private ApiInfo getApiInfo() {
		return new ApiInfo("MacysOrderMessageProducer REST API Documentation", "MacysOrderMessageProducer REST APIs released by Zensar Ltd.",
				"2.5", "http://zensar.com/termsofservice",
				new Contact("Srini", "http://srini.com", "sreenivasulu.c@zensar.com"), "GPL", "http://gpl.com",
				new ArrayList<VendorExtension>());
	}
	

	@Bean
	public ModelMapper getModelMapper() {

		return new ModelMapper();
	}
	
	@Bean
	public Docket getCustomizedDocket() {

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(getApiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.zensar"))
				// .paths(PathSelectors.ant("/advertise/category**")) // /advertise/status
				.paths(PathSelectors.any()).build();
	}
	
	
}
