package com.srm.asset.configuration;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build()
        .apiInfo(getApiInfo());
  }
  
  private ApiInfo getApiInfo() {
    return new ApiInfo(
            "srm-asset-api",
            "Rest API for front-end requests",
            "1.0",
            "TERMS OF SERVICE URL",
            new Contact("Danilo Oliveira","https://github.com/ds-oliveira","danilooliveira28@hotmail.com"),
            "LICENSE",
            "LICENSE URL",
            Collections.emptyList()
    );
}
}
