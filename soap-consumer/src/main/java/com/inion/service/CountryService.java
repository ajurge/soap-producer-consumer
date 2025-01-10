package com.inion.service;

import static com.inion.api.model.Country.CurrencyEnum.fromValue;

import com.inion.api.model.Country;
import com.inion.consumingwebservice.CountryClient;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CountryService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CountryService.class);

  private final CountryClient countryClient;

  public CountryService(CountryClient countryClient) {
    this.countryClient = countryClient;
  }

  public List<com.inion.api.model.Country> getCountries() {
    var restCountries = Stream.of("Spain", "Poland")
      .map(this::getAndMapSoapToRestCountry)
      .toList();

    LOGGER.info("Returning {} countries.", restCountries.size());

    return restCountries;
  }

  private Country getAndMapSoapToRestCountry(String country) {
    var soapCountry = countryClient.getCountry(country).getCountry();
    var restCountry = new com.inion.api.model.Country(
      soapCountry.getCapital(),
      fromValue(soapCountry.getCurrency().value()));
    restCountry.setName(Optional.of(soapCountry.getName()));
    restCountry.setPopulation(Optional.of(soapCountry.getPopulation()));

    return restCountry;
  }
}
