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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService, UserDetailsService {
  // UserDetailsService

  @Value("${API_KEY}")
  private String key;

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  public UserServiceImplementation(UserRepository userRepository,
      BCryptPasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

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
    }
    boolean isPasswordMatches = passwordEncoder.matches(userLoginDto.getPassword(),
        userRepository.findUserByUsername(
            userLoginDto.getUsername()).orElseThrow().getPassword());
    if (!isPasswordMatches) {
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

  public void removeCityFromMyList(Long id, City cityName) {
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

  public void register(User user) {
    if (user == null) {
      throw new UserNotFoundException();
    } else if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
      if (user.getUsername().isEmpty()) {
        throw new UsernameIsEmptyException();
      }
      throw new PasswordMissingException();
    } else if (userRepository.existsByUsername(user.getUsername())) {
      throw new UsernameIsTakenException();
    } else if (user.getUsername().length() < 4 && user.getPassword().length() < 8) {
      if (user.getUsername().length() < 4) {
        throw new UsernameIsTooShortException();
      }
      throw new PasswordIsTooShortException();
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findUserByUsername(username).orElseThrow(UserNotFoundException::new);
    Collection<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
    simpleGrantedAuthorities.add(new SimpleGrantedAuthority("user"));
    return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),simpleGrantedAuthorities);
  }
}
