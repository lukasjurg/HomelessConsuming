package team15.homelessconsuming.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import team15.homelessconsuming.model.User;

@Component
public class ApiClient {

    private static final String BASE_URL = "http://localhost:8080/api";
    private final RestTemplate restTemplate = new RestTemplate();

    public void registerUser(User user) {
        String url = BASE_URL + "/users";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        restTemplate.postForEntity(url, request, String.class);
    }
}
