package team15.homelessconsuming;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;

@SpringBootApplication
public class HomelessConsumingApplication implements CommandLineRunner {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "http://localhost:8080/api/users";

    public static void main(String[] args) {
        SpringApplication.run(HomelessConsumingApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Register User");
            System.out.println("2. Log In");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> registerUser(scanner);
                case 2 -> loginUser(scanner);
                case 3 -> {
                    System.out.println("Exiting the application. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void registerUser(Scanner scanner) {
        try {
            System.out.println("\nRegister New User");
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Email: ");
            String email = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            User user = new User(name, email, password);
            User response = restTemplate.postForObject(baseUrl, user, User.class);
            System.out.println("User registered successfully: " + response);
        } catch (HttpClientErrorException e) {
            System.out.println("Failed to register user: " + e.getStatusCode() + " : " + e.getResponseBodyAsString());
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private void loginUser(Scanner scanner) {
        try {
            System.out.println("\nLog In");
            System.out.print("Enter Email: ");
            String email = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            LoginRequest loginRequest = new LoginRequest(email, password);
            User response = restTemplate.postForObject(baseUrl + "/login", loginRequest, User.class);
            System.out.println("Login successful. Welcome, " + response.getUsername());
        } catch (HttpClientErrorException e) {
            System.out.println("Login failed: " + e.getStatusCode() + " : " + e.getResponseBodyAsString());
        } catch (Exception e) {
            System.out.println("An error occurred during login: " + e.getMessage());
        }
    }

    private static class User {
        private String username;
        private String email;
        private String password;

        // Constructors
        public User() {
        }

        public User(String username, String email, String password) {
            this.username = username;
            this.email = email;
            this.password = password;
        }

        // Getters and setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "User{" +
                    "username='" + username + '\'' +
                    ", email='" + email + '\'' +
                    '}';
        }
    }

    private static class LoginRequest {
        private String email;
        private String password;

        // Constructors
        public LoginRequest() {
        }

        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }

        // Getters and setters
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
