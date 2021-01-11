package com.tistory.ddojai.securitybasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityBasicApplication {
  public static void main(String[] args) {
    SpringApplication.run(SecurityBasicApplication.class, args);
  }
}
