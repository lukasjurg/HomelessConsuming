package team15.homelessconsuming.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import team15.homelessconsuming.model.AppService;
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

    public AppService createService(AppService service) {
        String url = BASE_URL + "/appservices";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AppService> request = new HttpEntity<>(service, headers);
        return restTemplate.postForObject(url, request, AppService.class);
    }

    public void updateService(int id, AppService service) {
        String url = BASE_URL + "/appservices/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AppService> request = new HttpEntity<>(service, headers);
        restTemplate.put(url, request);
    }

    public void deleteService(int id) {
        String url = BASE_URL + "/appservices/" + id;
        restTemplate.delete(url);
    }

}
