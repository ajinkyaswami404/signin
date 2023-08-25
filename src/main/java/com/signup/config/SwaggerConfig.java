package com.signup.config;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
public class SwaggerConfig {
  @Value("${controller.path}")
  private String path;

  @Bean
  public Docket productApi() {
    return new Docket(DocumentationType.SWAGGER_2).select().apis(basePackage(path)).build();
  }
}
