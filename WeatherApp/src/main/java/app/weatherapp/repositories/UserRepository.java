package app.weatherapp.repositories;

import app.weatherapp.models.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

  User findUserByUsernameAndPassword(String username,String password);

  User findUserByUsername(String name);

  boolean existsByUsername(String username);


}
