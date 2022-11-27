import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;


public class Main {

  public static void main(String[] args) throws IOException, InterruptedException {
    Scanner scanner = new Scanner(System.in);
    String location = scanner.nextLine();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(
            "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
                + location
                + "?unitGroup=metric&include=current&key=Y6F49K7ZB2SBZHTE9VAZRC5M2&contentType=json"))
        .method("GET", HttpRequest.BodyPublishers.noBody()).build();
    HttpResponse response = HttpClient.newHttpClient()
        .send(request, HttpResponse.BodyHandlers.ofString());
    JSO
    String reTemp = response.body().toString();
    System.out.println(reTemp);
  }
}
