package app.weatherapp.exceptions;

public class ListDoesntContainsCityException extends RuntimeException {

  public ListDoesntContainsCityException(String city) {
    super("City doesnt exist in your city list: " + city);
  }
}
