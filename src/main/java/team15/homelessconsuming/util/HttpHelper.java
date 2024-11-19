package team15.homelessconsuming.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

public class HttpHelper {

    private static final RestTemplate restTemplate = new RestTemplate();

    // Helper method to make GET requests
    public static <T> ResponseEntity<T> getRequest(String url, Class<T> responseType) {
        return restTemplate.getForEntity(url, responseType);
    }

    // Helper method to make POST requests
    public static <T> ResponseEntity<T> postRequest(String url, Object request, Class<T> responseType) {
        return restTemplate.postForEntity(url, request, responseType);
    }

    // Helper method to make PUT requests
    public static <T> ResponseEntity<T> putRequest(String url, Object request, Class<T> responseType) {
        return restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(request), responseType);
    }

    // Helper method to make DELETE requests
    public static void deleteRequest(String url) {
        restTemplate.delete(url);
    }
}