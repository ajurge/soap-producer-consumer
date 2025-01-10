package com.inion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SoapConsumerApplication {

  public static void main(String[] args) {
    SpringApplication.run(SoapConsumerApplication.class, args);
  }
}
