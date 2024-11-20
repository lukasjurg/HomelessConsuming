package team15.homelessconsuming;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;
import team15.homelessconsuming.model.User;

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
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> registerUser(scanner);
                case 2 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void registerUser(Scanner scanner) {
        System.out.println("\nRegister New User");
        System.out.print("Enter Name: ");
        String username = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        User user = new User(username, email, password);

        try {
            User response = restTemplate.postForObject(baseUrl, user, User.class);
            System.out.println("User registered successfully: " + response);
        } catch (Exception e) {
            System.out.println("Failed to register user: " + e.getMessage());
        }
    }
}
