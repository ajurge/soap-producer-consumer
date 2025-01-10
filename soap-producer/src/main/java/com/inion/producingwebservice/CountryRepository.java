package com.inion.producingwebservice;

import io.spring.guides.gs_producing_web_service.Country;
import io.spring.guides.gs_producing_web_service.Currency;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class CountryRepository {

  private static final Map<String, Country> countries = new HashMap<>();

  @PostConstruct
  public void initData() {
    var spain = new Country();
    spain.setName("Spain");
    spain.setCapital("Madrid");
    spain.setCurrency(Currency.EUR);
    spain.setPopulation(46704314);

    countries.put(spain.getName(), spain);

    var poland = new Country();
    poland.setName("Poland");
    poland.setCapital("Warsaw");
    poland.setCurrency(Currency.PLN);
    poland.setPopulation(38186860);

    countries.put(poland.getName(), poland);

    var uk = new Country();
    uk.setName("United Kingdom");
    uk.setCapital("London");
    uk.setCurrency(Currency.GBP);
    uk.setPopulation(63705000);

    countries.put(uk.getName(), uk);
  }

  public Country findCountry(String name) {
    Assert.notNull(name, "The country's name must not be null");
    return countries.get(name);
  }
}
