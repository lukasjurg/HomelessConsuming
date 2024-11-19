package team15.homelessconsuming.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import team15.homelessconsuming.model.ServiceCategory;

@Component
public class ApiClient {

    private static final String BASE_URL = "http://localhost:8080/api";
    private final RestTemplate restTemplate = new RestTemplate();

    public ServiceCategory[] getAllServiceCategories() {
        String url = BASE_URL + "/servicecategories";
        ResponseEntity<ServiceCategory[]> response = restTemplate.getForEntity(url, ServiceCategory[].class);
        return response.getBody();
    }
}