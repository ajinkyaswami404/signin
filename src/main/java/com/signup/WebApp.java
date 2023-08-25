package com.signup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class WebApp {
  private static final Logger logger = LogManager.getLogger(WebApp.class);

  public static void main(String[] args) {
    SpringApplication.run(WebApp.class, args);
    logger.info("executed successfully");
  }
}
