package io.github.ddojai;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Application {
  private static final String APPLICATION_LOCATIONS = "spring.config.location="
    + "classpath:application.yml";

  public static void main(String[] args) {
    new SpringApplicationBuilder(Application.class)
      .properties(APPLICATION_LOCATIONS)
      .run(args);
  }
}
