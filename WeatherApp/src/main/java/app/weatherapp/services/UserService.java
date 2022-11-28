package app.weatherapp.services;

import app.weatherapp.dtos.UserLoginDto;
import app.weatherapp.models.City;
import app.weatherapp.models.User;
import java.io.IOException;
import java.util.List;

public interface UserService {

  User login(UserLoginDto userLoginDto);

  List<City> ListMyCities(Long id);

  void addCityToMyList(Long id,City cityName);

  public void removeCityFromMyList(Long id, City cityName);

  User findUserById(Long id);

  String getInfoFromCity(String cityname) throws IOException, InterruptedException;

}
