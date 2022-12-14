package app.weatherapp.configuration;

import app.weatherapp.services.TokenServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  public SecurityConfig(UserDetailsService userDetailsService,
      BCryptPasswordEncoder passwordEncoder, TokenServices tokenServices) {
    this.userDetailsService = userDetailsService;
    this.passwordEncoder = passwordEncoder;
    this.tokenServices = tokenServices;
  }


  private final UserDetailsService userDetailsService;

  private final BCryptPasswordEncoder passwordEncoder;

  private final TokenServices tokenServices;


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(
        AuthenticationManagerBuilder.class);
    authenticationManagerBuilder.userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder);
    http
        .csrf(csrf -> csrf.disable())
        .authorizeRequests().antMatchers("/register").permitAll().and()
        .authorizeRequests().antMatchers("/login").permitAll().and()
        .authorizeRequests().antMatchers("/**").authenticated().and()
        .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    http.apply(AuthConfig.getAuthConfig(tokenServices));
    return http.build();
  }


}
