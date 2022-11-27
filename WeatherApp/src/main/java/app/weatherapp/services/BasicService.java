package app.weatherapp.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;


public class BasicService{

  private String key = System.getenv("API_KEY");

  public void call() throws IOException, InterruptedException {
    //Scanner scanner = new Scanner(System.in);

    String location = "Budapest";
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(
            "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
                + location
                + "?unitGroup=metric&include=current&key="+key+"&contentType=json"))
        .method("GET", HttpRequest.BodyPublishers.noBody()).build();
    HttpResponse response = HttpClient.newHttpClient()
        .send(request, HttpResponse.BodyHandlers.ofString());
    JSONObject jsonObject = new JSONObject(response);
    String reTemp = response.body().toString();
    System.out.println("temp"+jsonObject.getString("temp"));
  }

}
