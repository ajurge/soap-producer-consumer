package com.inion.controller;

import com.inion.api.CountriesApi;
import com.inion.api.model.Country;
import com.inion.service.CountryService;
import io.swagger.annotations.Api;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Api(tags = {"Countries"})
public class CountryController implements CountriesApi {

  private final CountryService countryService;

  public CountryController(CountryService countryService) {
    this.countryService = countryService;
  }

  @Override
  public ResponseEntity<List<Country>> getCountries() {
    return ResponseEntity.ok(this.countryService.getCountries());
  }
}
