package com.inion.consumingwebservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class CountryConfiguration {

  public static String getSoapProducerUlr() {
    var soapProducerUl = System.getenv("SOAP_PRODUCER_URL");
    if (soapProducerUl == null) {
      soapProducerUl = "localhost";
    }
    return "http://" + soapProducerUl + ":8081/ws";
  }

  @Bean
  public Jaxb2Marshaller marshaller() {
    var marshaller = new Jaxb2Marshaller();
    // this package must match the package in the <generatePackage> specified in
    // pom.xml
    marshaller.setContextPath("com.inion.consumingwebservice.wsdl");
    return marshaller;
  }

  @Bean
  public CountryClient countryClient(Jaxb2Marshaller marshaller) {
    var client = new CountryClient();

    client.setDefaultUri(getSoapProducerUlr());
    client.setMarshaller(marshaller);
    client.setUnmarshaller(marshaller);
    return client;
  }

}
