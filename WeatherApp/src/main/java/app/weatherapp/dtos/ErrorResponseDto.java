package app.weatherapp.dtos;

public class ErrorResponseDto {

  private String message;

  public ErrorResponseDto(String message) {
    this.message = message;
  }

  public ErrorResponseDto() {
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
