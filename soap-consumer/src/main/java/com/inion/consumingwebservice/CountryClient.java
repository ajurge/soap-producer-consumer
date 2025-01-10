package com.inion.consumingwebservice;

import static com.inion.consumingwebservice.CountryConfiguration.getSoapProducerUlr;

import com.inion.consumingwebservice.wsdl.GetCountryRequest;
import com.inion.consumingwebservice.wsdl.GetCountryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class CountryClient extends WebServiceGatewaySupport {

  private static final Logger LOGGER = LoggerFactory.getLogger(CountryClient.class);

  public GetCountryResponse getCountry(String country) {

    var request = new GetCountryRequest();
    request.setName(country);

    LOGGER.info("Requesting location for " + country);

    // TODO: maybe refactor this to use soap feign client
    return (GetCountryResponse) getWebServiceTemplate()
      .marshalSendAndReceive(getSoapProducerUlr() + "/countries", request,
        new SoapActionCallback(
          "http://spring.io/guides/gs-producing-web-service/GetCountryRequest"));
  }

}
