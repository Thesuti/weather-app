package app.weatherapp.configuration;

import app.weatherapp.models.City;
import app.weatherapp.models.User;
import app.weatherapp.repositories.CityRepository;
import app.weatherapp.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration implements CommandLineRunner{

  private final UserRepository userRepository;
  private final CityRepository cityRepository;

  public UserConfiguration(UserRepository userRepository, CityRepository cityRepository) {
    this.userRepository = userRepository;
    this.cityRepository = cityRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    User user = new User("chris","1234");
    userRepository.save(user);
    City city = new City("Budapest");
    cityRepository.save(city);
  }
}
