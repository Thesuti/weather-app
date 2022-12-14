package app.weatherapp.controllers;


import app.weatherapp.dtos.LoginRequest;
import app.weatherapp.services.TokenServices;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

  public AuthController(TokenServices tokenServices) {
    this.tokenServices = tokenServices;
  }

  private final TokenServices tokenServices;



  /*@PostMapping("/token")
  public String token(@RequestBody LoginRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(),loginRequest.password()));
    System.out.println(loginRequest.username());
    return tokenServices.generateToken(authentication);
  }*/


}
