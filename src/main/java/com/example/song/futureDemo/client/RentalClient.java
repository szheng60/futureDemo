package com.example.song.futureDemo.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RentalClient {

    private final String endpoint;
    private final RestTemplate restTemplate;

    private void displayInfo() {
        System.out.println(Thread.currentThread());
    }

    public RentalClient(@Qualifier("endpoint") String endpoint, RestTemplate restTemplate) {
        this.endpoint = endpoint;
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> getUserProfile(String email) {
        displayInfo();
        String uri = endpoint + "/api/viewprofile?userEmail=" + email;
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        return response;
    }
}
