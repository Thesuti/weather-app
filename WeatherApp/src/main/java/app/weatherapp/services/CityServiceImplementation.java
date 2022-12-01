package app.weatherapp.services;

import app.weatherapp.models.City;
import app.weatherapp.repositories.CityRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImplementation {
/*

  public CityServiceImplementation(CityRepository cityRepository) {
    this.cityRepository = cityRepository;
  }

  private final CityRepository cityRepository;

  public City save(String cityName){
    if (!isCityExist(cityName)) {
      City city = new City(cityName);
      cityRepository.save(city);
    }
    return cityRepository.findCitiesByCityName(cityName);
  }
  public boolean isCityExist(String cityName){
    return cityRepository.existByCityName(cityName);
  }
*/

}
