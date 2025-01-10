package com.inion.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

  @Bean
  public OpenAPI springDocOpenAPI() {
    return new OpenAPI()
      .info(
        new Info()
          .title("Demo API")
          .description("The API for the Demo app")
          .version("1.0")
          .contact(
            new Contact()
              .name("Inion Spring Boot template")
              .url("www.inion.com")
              .email("info@inion.com"))
          .license(new License().name("").url("")));
  }
}
