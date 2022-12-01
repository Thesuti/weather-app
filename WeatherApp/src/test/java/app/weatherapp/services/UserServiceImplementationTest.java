package app.weatherapp.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.mockito.Mockito;


class UserServiceImplementationTest {

  public UserServiceImplementationTest(UserService userService) {
    this.userService = userService;
  }

  private final UserService userService;

public void List_Cities(){
  Mockito.when(userService.ListMyCities(0L)).thenReturn(new ArrayList<>());
  //  assertArrayEquals();
}

}