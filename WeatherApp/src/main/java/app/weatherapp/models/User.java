package app.weatherapp.models;

import app.weatherapp.exceptions.AlreadySavedCityException;
import app.weatherapp.exceptions.ListDoesntContainsCityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String username;

  private String password;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "user_cities",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "cities_id"))
  private List<City> myCities;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public User(String username, String password) {
    this.username = username;
    this.password = password;
    this.myCities = new ArrayList<>();
  }

  public User() {
  }

  public List<City> getCities() {
    return myCities;
  }


  public void addCity(City city) {
    if (myCities.contains(city)) {
      throw new AlreadySavedCityException(city.getCityName());
    }
    myCities.add(city);
  }

  public void removeCity(City city) {
    if (!myCities.contains(city)) {
      throw new ListDoesntContainsCityException(city.getCityName());
    }
    myCities.remove(city);
  }

}
