package app.weatherapp.services;

import app.weatherapp.exceptions.*;
import app.weatherapp.models.City;
import app.weatherapp.models.JSON.Data;
import app.weatherapp.models.User;
import app.weatherapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImplementation implements UserService, UserDetailsService {

    @Value("${API_KEY}")
    private String key;

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final RestTemplate restTemplate;

    public UserServiceImplementation(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.restTemplate = restTemplate;
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
        Data data = restTemplate.getForObject(("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
                + cityname
                + "?unitGroup=metric&include=current&key=" + key), Data.class);
        return data.getDays()[0].toString();
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
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), simpleGrantedAuthorities);
    }
}
