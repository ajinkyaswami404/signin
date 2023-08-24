package com.signup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@EnableSwagger2
@EnableTransactionManagement
@SpringBootApplication
public class WebApp {
  private static final Logger logger = LogManager.getLogger(WebApp.class);

  @Value("${controller.path}")
  private String path;

  public static void main(String[] args) {
    SpringApplication.run(WebApp.class, args);
    logger.info("executed successfully");
  }

  @Bean
  public Docket productApi() {

    return new Docket(SWAGGER_2).select().apis(basePackage(path)).build();
  }
}
