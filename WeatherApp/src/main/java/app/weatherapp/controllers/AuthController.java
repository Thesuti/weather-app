package app.weatherapp.controllers;



import app.weatherapp.services.TokenServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

  private static final Logger log = LoggerFactory.getLogger(AuthController.class);

  public AuthController(TokenServices tokenServices) {
    this.tokenServices = tokenServices;
  }

  private final TokenServices tokenServices;

  @PostMapping("/token")
  public String token(Authentication authentication){
    log.debug("Token requested");
  String token = tokenServices.generateToken(authentication);
  log.debug("Token granted");
  return token;
  }


}
