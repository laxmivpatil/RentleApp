package com.techverse.controller;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import io.jsonwebtoken.io.IOException;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import retrofit2.Retrofit; 
@RestController
public class instaapicontroller {

	 private static final String BASE_URL = "https://v3.saveinsta.app/api/ajaxSearch/";

	 @GetMapping("/instaapi")
	    public String callApi() {
	        String apiUrl = "https://v3.saveinsta.app/api/ajaxSearch/";
	        String q = "https://www.instagram.com/patelajeet692/";
	        String t = "media";
	        String lang = "en";
	        String accept = "*/*";
	        String acceptEncoding = "gzip, deflate, br, zstd";
	        String acceptLanguage = "en-GB,en-US;q=0.9,en;q=0.8,hi;q=0.7";
	        String contentType = "application/json;charset=utf-8";
	        String origin = "https://saveig.app";
	        String priority = "u=1, i";
	        String referer = "https://saveig.app/";
	        String secChUa = "\"Chromium\";v=\"124\", \"Google Chrome\";v=\"124\", \"Not-A.Brand\";v=\"99\"";
	        String secChUaMobile = "?0";
	        String secChUaPlatform = "\"Windows\"";
	        String secFetchDest = "empty";
	        String secFetchMode = "cors";
	        String secFetchSite = "same-site";
	        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36";

	        try {
	            URL url = new URL(apiUrl + "?q=" + q + "&t=" + t + "&lang=" + lang);
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setRequestMethod("GET");
	            connection.setRequestProperty("Accept", accept);
	            connection.setRequestProperty("Accept-Encoding", acceptEncoding);
	            connection.setRequestProperty("Accept-Language", acceptLanguage);
	            connection.setRequestProperty("Content-Type", contentType);
	            connection.setRequestProperty("Origin", origin);
	            connection.setRequestProperty("Priority", priority);
	            connection.setRequestProperty("Referer", referer);
	            connection.setRequestProperty("Sec-Ch-Ua", secChUa);
	            connection.setRequestProperty("Sec-Ch-Ua-Mobile", secChUaMobile);
	            connection.setRequestProperty("Sec-Ch-Ua-Platform", secChUaPlatform);
	            connection.setRequestProperty("Sec-Fetch-Dest", secFetchDest);
	            connection.setRequestProperty("Sec-Fetch-Mode", secFetchMode);
	            connection.setRequestProperty("Sec-Fetch-Site", secFetchSite);
	            connection.setRequestProperty("User-Agent", userAgent);

	            int responseCode = connection.getResponseCode();
	            if (responseCode == HttpURLConnection.HTTP_OK) {
	                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)); // Specify UTF-8 encoding
	                String line;
	                StringBuilder response = new StringBuilder(); // Use StringBuilder for better performance

	                while ((line = reader.readLine()) != null) {
	                    response.append(line);
	                }
	                reader.close();

	                // Return the API response as JSON string
	                return response.toString();
	            } else {
	                // Handle unsuccessful response
	                System.err.println("Failed to get stories. Response code: " + responseCode);
	            }
	            connection.disconnect();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return "{}"; // Return an empty JSON object if there's an error
	    }

}
		
	
	

