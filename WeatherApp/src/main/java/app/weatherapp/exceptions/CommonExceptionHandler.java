package app.weatherapp.exceptions;

import app.weatherapp.dtos.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponseDto handleUserNotFoundException() {
    return new ErrorResponseDto("User not found");
  }

  @ExceptionHandler(PasswordMissingException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handlePasswordMissingException() {
    return new ErrorResponseDto("Password is missing");
  }

  @ExceptionHandler(UsernameIsEmptyException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleUsernameIsEmptyException() {
    return new ErrorResponseDto("Username is missing");
  }

  @ExceptionHandler(InvalidLoginCredentialsException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleInvalidLoginCredentialsException() {
    return new ErrorResponseDto("Invalid login credentials");
  }

  @ExceptionHandler({AlreadySavedCityException.class, ListDoesntContainsCityException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleAlreadySavedCityException(Exception exception) {
    return new ErrorResponseDto(exception.getMessage());
  }

  @ExceptionHandler(UsernameIsTakenException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleUsernameIsTakenException() {
    return new ErrorResponseDto("Username is already taken");
  }

  @ExceptionHandler(UsernameIsTooShortException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleUsernameIsTooShortException() {
    return new ErrorResponseDto("Username is too short");
  }

  @ExceptionHandler(PasswordIsTooShortException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handlePasswordIsTooShortException() {
    return new ErrorResponseDto("Password is too short");
  }

}
