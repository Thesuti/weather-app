package app.weatherapp;

import app.weatherapp.configuration.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class WeatherAppApplication {


  public static void main(String[] args) {
    SpringApplication.run(WeatherAppApplication.class, args);
  }

}
