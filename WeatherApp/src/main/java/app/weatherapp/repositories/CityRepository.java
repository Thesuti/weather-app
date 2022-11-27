package app.weatherapp.repositories;

import app.weatherapp.models.City;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City,Long> {

  /*boolean existByCityName(String name);

  City findCitiesByCityName(String name);*/

}
