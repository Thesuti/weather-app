package app.weatherapp.configuration;

import app.weatherapp.services.TokenServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class AuthFilter extends UsernamePasswordAuthenticationFilter {

  public AuthFilter(AuthenticationManager authenticationManager, TokenServices tokenServices) {
    this.authenticationManager = authenticationManager;
    this.tokenServices = tokenServices;
  }

  private final AuthenticationManager authenticationManager;

  private final TokenServices tokenServices;

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    System.out.println("asd");
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    System.out.println(username + "         " + password);
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,
        password);
    return authenticationManager.authenticate(token);
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException, ServletException {
    String token = tokenServices.generateToken(authResult);
    new ObjectMapper().writeValue(response.getOutputStream(), token);
  }
}
