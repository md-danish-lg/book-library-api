package com.danish.booklibrary;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AiService {

    private final RestTemplate restTemplate;
    private final String pythonServiceURL = "http://localhost:8000";

    public AiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String summarize(String text){
        Map<String, String> request = Map.of("text", text);
        Map response = restTemplate.postForObject(
                pythonServiceURL + "/summarize",
                request,
                Map.class
        );
        return (String) response.get("summary");
    }
}
