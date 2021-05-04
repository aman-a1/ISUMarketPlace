package com309.springboot.isumarketplace.JavaDoc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.builders.ApiInfoBuilder;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {
    @Bean
	  public Docket myDocket() {
		  return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com309.springboot.isumarketplace"))
				.paths(PathSelectors.any())
				.build();
	  }
	
	  private ApiInfo apiInfo() {
		  return new ApiInfoBuilder()
				.title("COMS309 VB-05(ISU MarketPlace Project)")
				.description("Swagger REST APIs Doc for COMS309-ISU MarketPlace Project")
				.termsOfServiceUrl("https://coms-309-vb-05.cs.iastate.edu:8080/terms")
				.contact(new Contact("Muhammad Khairi Norizan","https://git.linux.iastate.edu/cs309/fall2020/VB_5",
						"mkhairi@iastate.edu"))
				.version("0.0.2")
				.build();
	}
    
}
