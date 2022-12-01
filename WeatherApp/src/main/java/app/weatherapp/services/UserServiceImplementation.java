package app.weatherapp.services;

import app.weatherapp.dtos.UserLoginDto;
import app.weatherapp.exceptions.InvalidLoginCredentialsException;
import app.weatherapp.exceptions.PasswordIsTooShortException;
import app.weatherapp.exceptions.PasswordMissingException;
import app.weatherapp.exceptions.UserNotFoundException;
import app.weatherapp.exceptions.UsernameIsEmptyException;
import app.weatherapp.exceptions.UsernameIsTakenException;
import app.weatherapp.exceptions.UsernameIsTooShortException;
import app.weatherapp.models.City;
import app.weatherapp.models.User;
import app.weatherapp.repositories.UserRepository;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {

  public UserServiceImplementation(UserRepository userRepository) {
    this.userRepository = userRepository;
    this.passwordEncoder = new BCryptPasswordEncoder();
  }

  @Value("${API_KEY}")
  private String key;

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public User login(UserLoginDto userLoginDto) {
    if (userLoginDto.getUsername() == null || userLoginDto.getPassword() == null) {
      throw new UserNotFoundException();
    } else if (userLoginDto.getUsername().isEmpty() || userLoginDto.getPassword().isEmpty()) {
      if (userLoginDto.getUsername().isEmpty()) {
        throw new UsernameIsEmptyException();
      }
      throw new PasswordMissingException();
    } else if (!userRepository.existsByUsername(userLoginDto.getUsername())) {
      throw new UserNotFoundException();
    } else if (!userRepository.findUserByUsername(userLoginDto.getUsername()).getPassword()
        .equals(userLoginDto.getPassword())) {
      throw new InvalidLoginCredentialsException();
    }
    return userRepository.findUserByUsernameAndPassword(userLoginDto.getUsername(),
        userLoginDto.getPassword());
  }

  public List<City> ListMyCities(Long id) {
    return findUserById(id).getCities();
  }

  public void addCityToMyList(Long id, City cityName) {
    User user = findUserById(id);
    user.addCity(cityName);
    userRepository.save(user);
  }

  public void removeCityFromMyList(Long id, City cityName){
    User user = findUserById(id);
    user.removeCity(cityName);
    userRepository.save(user);
  }

  public User findUserById(Long id) {
    return userRepository.findById(id).orElseThrow();
  }

  public String getInfoFromCity(String cityname) throws IOException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(
            "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
                + cityname
                + "?unitGroup=metric&include=current&key=" + key + "&contentType=json"))
        .method("GET", HttpRequest.BodyPublishers.noBody()).build();
    HttpResponse response = HttpClient.newHttpClient()
        .send(request, HttpResponse.BodyHandlers.ofString());
    JSONObject jsonObject = new JSONObject(response.body().toString());
    JSONObject days = new JSONObject(jsonObject.get("days"));
    System.out.println(days);
    JSONArray jsonArray = jsonObject.getJSONArray("days");
    return "current temperature in " + cityname + " " + ((JSONObject) jsonArray.get(
        0)).getBigDecimal("temp").toString() +
        "\n" + ((JSONObject) jsonArray.get(0)).get("description").toString();
  }
  
  public void register(User user){
    if (user == null) {
      throw new UserNotFoundException();
    } else if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
      if (user.getUsername().isEmpty()) {
        throw new UsernameIsEmptyException();
      }
      throw new PasswordMissingException();
    } else if (userRepository.existsByUsername(user.getUsername())) {
      throw new UsernameIsTakenException();
    } else if (user.getUsername().length()<4 && user.getPassword().length()<8) {
      if (user.getUsername().length()<4) {
        throw new UsernameIsTooShortException();
      }
      throw new PasswordIsTooShortException();
    }
    String encodedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(encodedPassword);
    userRepository.save(user);
  }
}
