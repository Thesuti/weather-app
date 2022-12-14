package app.weatherapp.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class UserServiceImplementationTest {

  public UserServiceImplementationTest(UserService userService) {
    this.userService = userService;
  }
  @MockBean
  private final UserService userService;


@Test
public void List_Cities(){
  Mockito.when(userService.ListMyCities(0L)).thenReturn(new ArrayList<>());
    assertEquals(0,userService.ListMyCities(0L).size());
}

}