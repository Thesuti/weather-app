package app.weatherapp.models;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class City {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true)
  private String cityName;

  @ManyToMany(mappedBy = "myCities" , cascade = CascadeType.ALL)
  private List<User> userList;

  public City(String cityName) {
    this.cityName = cityName;
  }

  public City() {
  }

  public String getCityName() {
    return cityName;
  }

  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

  public Long getId() {
    return id;
  }
}
