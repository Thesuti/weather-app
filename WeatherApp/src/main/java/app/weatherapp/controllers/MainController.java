package app.weatherapp.controllers;

import app.weatherapp.dtos.UserLoginDto;
import app.weatherapp.models.City;
import app.weatherapp.models.User;
import app.weatherapp.services.UserService;
import java.io.IOException;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

  public MainController(UserService userService) {
    this.userService = userService;
  }

  private final UserService userService;

  @PostMapping("/login")
  public ResponseEntity login(@RequestBody UserLoginDto userLoginDto) {
    return ResponseEntity.ok().body(userService.login(userLoginDto));
  }

  @GetMapping("/mycities/{id}")
  public ResponseEntity listMyCities(@PathVariable long id) {
    return ResponseEntity.ok().body(userService.ListMyCities(id));
  }

  @PostMapping("/add/mycities/{id}")
  public ResponseEntity addToMyCities(@PathVariable long id, @RequestBody City cityName) {
    userService.addCityToMyList(id, cityName);
    return ResponseEntity.ok().body("added to your list");
  }

  @GetMapping("/info")
  public ResponseEntity getCityInfo(@RequestParam String cityName)
      throws IOException, InterruptedException {
    return ResponseEntity.ok().body(userService.getInfoFromCity(cityName));
  }

  @PostMapping("/delete/mycities/{id}")
  public ResponseEntity deleteCityFromMyList(@PathVariable long id, @RequestBody City cityName) {
    userService.removeCityFromMyList(id, cityName);
    return ResponseEntity.ok().body("City removed from your list");
  }

  @PostMapping("/register")
  public ResponseEntity register(@RequestBody User user){
    userService.register(user);
    return ResponseEntity.ok().body("Successfully registered");
  }

}
