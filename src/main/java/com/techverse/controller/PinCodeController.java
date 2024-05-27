package com.techverse.controller;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
public class PinCodeController {

    @GetMapping("/api/pincode")
    public  ResponseEntity<Map<String,Object>> generateOrder(@RequestParam String pincode) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://india-pincode-with-latitude-and-longitude.p.rapidapi.com/api/v1/pincode/" + pincode)
                .get()
                .addHeader("X-RapidAPI-Key", "28e5aa9d3fmshb5b34846ce79043p14862fjsna60130063e65")
                .addHeader("X-RapidAPI-Host", "india-pincode-with-latitude-and-longitude.p.rapidapi.com")
                .build();
        Set<String> districts = new HashSet<>();
        Set<String> states = new HashSet<>();
        try (Response response = client.newCall(request).execute()) {
        	
        	 if (!response.isSuccessful()) {
                 throw new IOException("Unexpected response code: " + response.code());
             }

             ObjectMapper objectMapper = new ObjectMapper();
             ArrayNode arrayNode = objectMapper.readValue(response.body().string(), ArrayNode.class);
             for (int i = 0; i < arrayNode.size(); i++) {
                 districts.add(arrayNode.get(i).get("district").asText());
                 states.add(arrayNode.get(i).get("state").asText());
             }
             Map<String, Object> response1 = new HashMap<>();
            response1.put("districts", districts);
             response1.put("states", states);
             response1.put("message", "valid pincode");
             response1.put("status", true);
             return ResponseEntity.status(HttpStatus.OK).body(response1);
           
          
        } catch (IOException e) {
            
        	 Map<String, Object> response1 = new HashMap<>();
             response1.put("districts", districts);
              response1.put("states", states);

              response1.put("status", false);
              response1.put("message", "Invalid pincode");

            return ResponseEntity.status(HttpStatus.OK).body(response1);
        }
    }
}
