package team15.homelessconsuming.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

public class ErrorHandlingUtil {

    // Handles client errors (4xx)
    public static void handleClientError(HttpClientErrorException e) {
        System.err.println("Client error: " + e.getStatusCode() + " " + e.getResponseBodyAsString());
    }

    // Handles server errors (5xx)
    public static void handleServerError(HttpServerErrorException e) {
        System.err.println("Server error: " + e.getStatusCode() + " " + e.getResponseBodyAsString());
    }

    // General method to check if response is successful
    public static boolean isSuccessful(HttpStatus status) {
        return status.is2xxSuccessful();
    }
}