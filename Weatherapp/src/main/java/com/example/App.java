package com.example;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class App {
    public static void main(String[] args) throws Exception {
        String apiKey = "7c51019338db6a416fa09f226ced1ada";
        String city;
        Scanner s1 = new Scanner(System.in);
        
        System.out.print("Enter city name: ");
        city = s1.nextLine();
        if(city != null)
        {
            //create a client  to make the request later on to the openweathermap.
        HttpClient client = HttpClient.newHttpClient();
        // Builds the request to the openweather Api.
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.openweathermap.org/data/2.5/forecast?q="+ city + "&appid=" + apiKey + "&units=metric")).build();
        //Sends the request and gives of the reponse as a string
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //Build the json file to filter it later on
        JsonObject root = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonArray list = root.getAsJsonArray("list");

        // Filtering the output of the body
        for (JsonElement elem : list) {
            JsonObject obj = elem.getAsJsonObject();

            String time = obj.get("dt_txt").getAsString();
            double temp = obj.getAsJsonObject("main").get("temp").getAsDouble();
            String desc = obj.getAsJsonArray("weather")
                             .get(0).getAsJsonObject()
                             .get("description").getAsString();

            System.out.println("It's "+ time + " with a temperature of " + temp + "°C, " + "and the current weather condition is " +desc);
            break;
        }
        }
        else
        {
            System.out.println("City name is spelled wrong or not found. Please try again.");
        }
        
        
    }
}


