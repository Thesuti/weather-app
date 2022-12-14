package app.weatherapp.configuration;

import app.weatherapp.services.TokenServices;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

public class AuthConfig extends AbstractHttpConfigurer<AuthConfig, HttpSecurity> {

  private final TokenServices tokenServices;

  public AuthConfig(TokenServices tokenServices) {
    this.tokenServices = tokenServices;
  }

  @Override
  public void configure(HttpSecurity builder) throws Exception {
    AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
    builder.addFilter(new AuthFilter(authenticationManager, tokenServices));
  }
  public static AuthConfig getAuthConfig(TokenServices tokenServices){
    return new AuthConfig(tokenServices);
  }

}
