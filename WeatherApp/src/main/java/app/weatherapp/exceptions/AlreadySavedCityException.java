package app.weatherapp.exceptions;

public class AlreadySavedCityException extends RuntimeException {

  public AlreadySavedCityException(String city) {
    super("City already exist in your city list: "+city);
  }
}
