package com.example;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class App {
    public static void main(String[] args) throws Exception {
        String apiKey = "7c51019338db6a416fa09f226ced1ada";
        String city = "Saarbrücken";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openweathermap.org/data/2.5/forecast?q="
                        + city + "&appid=" + apiKey + "&units=metric"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Parse JSON
        JsonObject root = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonArray list = root.getAsJsonArray("list");

        // Filtered output
        for (JsonElement elem : list) {
            JsonObject obj = elem.getAsJsonObject();

            String time = obj.get("dt_txt").getAsString();
            double temp = obj.getAsJsonObject("main").get("temp").getAsDouble();
            String desc = obj.getAsJsonArray("weather")
                             .get(0).getAsJsonObject()
                             .get("description").getAsString();

            System.out.println(time + " → " + temp + "°C, " + desc);
            break;
        }
    }
}


