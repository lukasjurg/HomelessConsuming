package team15.homelessconsuming;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import team15.homelessconsuming.model.AppService;
import team15.homelessconsuming.model.LoginRequest;
import team15.homelessconsuming.model.LoginResponse;
import team15.homelessconsuming.model.User;

import java.util.Scanner;

@SpringBootApplication
public class HomelessConsumingApplication implements CommandLineRunner {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "http://localhost:8080/api/users";
    private final String serviceUrl = "http://localhost:8080/api/appservices"; // Replace with actual backend service URL

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
            scanner.nextLine();

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
            System.out.print("Enter Role (User/Admin): ");
            String role = scanner.nextLine();

            User user = new User(name, email, password);
            String registerUrl = baseUrl + "/register?role=" + role;
            User response = restTemplate.postForObject(registerUrl, user, User.class);
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

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<LoginRequest> requestEntity = new HttpEntity<>(loginRequest, headers);

            LoginResponse response = restTemplate.postForObject(baseUrl + "/login", requestEntity, LoginResponse.class);

            if ("User".equalsIgnoreCase(response.getRole())) {
                userMenu(scanner, response);
            } else if ("Admin".equalsIgnoreCase(response.getRole())) {
                adminMenu(scanner, response);
            } else {
                System.out.println("Unknown role. Unable to provide a menu.");
            }
        } catch (HttpClientErrorException e) {
            System.out.println("Login failed: " + e.getStatusCode() + " : " + e.getResponseBodyAsString());
        } catch (Exception e) {
            System.out.println("An error occurred during login: " + e.getMessage());
        }
    }

    private void userMenu(Scanner scanner, LoginResponse user) {
        System.out.println("Welcome, " + user.getUsername() + " (User)");
        while (true) {
            System.out.println("\nUser Menu:");
            System.out.println("1. View Profile");
            System.out.println("2. Update Profile");
            System.out.println("3. View Services");
            System.out.println("4. Give Feedback");
            System.out.println("5. Log Out");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> System.out.println("User Profile: " + user);
                case 2 -> updateUserProfile(scanner, user.getId());
                case 3 -> viewServices(scanner);
                case 4 -> System.out.println("Give Feedback functionality is under development.");
                case 5 -> {
                    System.out.println("Logging out. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewServices(Scanner scanner) {
        while (true) {
            System.out.println("\nView Services Menu:");
            System.out.println("1. Print out all available services");
            System.out.println("2. Go back");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> printAllServices();
                case 2 -> {
                    System.out.println("Returning to the main menu...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void printAllServices() {
        try {
            AppService[] services = restTemplate.getForObject(serviceUrl, AppService[].class);

            if (services != null && services.length > 0) {
                System.out.println("\nAvailable Services:");
                for (AppService service : services) {
                    System.out.println("-----------------------------------");
                    System.out.println("Service ID: " + service.getId());
                    System.out.println("Name: " + service.getName());
                    System.out.println("Address: " + service.getAddress());
                    System.out.println("Contact Number: " + service.getContactNumber());
                    System.out.println("Start Time: " + service.getStartTime());
                    System.out.println("End Time: " + service.getEndTime());
                    System.out.println("City: " + (service.getCity() != null ? service.getCity().getName() : "N/A"));
                    System.out.println("Category: " + (service.getCategory() != null ? service.getCategory().getName() : "N/A"));
                    System.out.println("-----------------------------------");
                }
            } else {
                System.out.println("No services are currently available.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while fetching services: " + e.getMessage());
        }
    }


    private void updateUserProfile(Scanner scanner, int userId) {
        try {
            System.out.println("\nUpdate Profile");
            System.out.print("Enter New Name (leave blank to keep unchanged): ");
            String newName = scanner.nextLine();
            System.out.print("Enter New Email (leave blank to keep unchanged): ");
            String newEmail = scanner.nextLine();
            System.out.print("Enter New Password (leave blank to keep unchanged): ");
            String newPassword = scanner.nextLine();

            User updatedUser = new User();
            if (!newName.isBlank()) updatedUser.setUsername(newName);
            if (!newEmail.isBlank()) updatedUser.setEmail(newEmail);
            if (!newPassword.isBlank()) updatedUser.setPassword(newPassword);

            String updateUrl = baseUrl + "/" + userId + "/update-profile";

            restTemplate.put(updateUrl, updatedUser);
            System.out.println("Profile updated successfully!");
        } catch (HttpClientErrorException e) {
            System.out.println("Failed to update profile: " + e.getStatusCode() + " : " + e.getResponseBodyAsString());
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private void adminMenu(Scanner scanner, LoginResponse admin) {
        System.out.println("Welcome, " + admin.getUsername() + " (Admin)");
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. View All Users");
            System.out.println("2. Manage Services");
            System.out.println("3. Log Out");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> viewAllUsers();
                case 2 -> System.out.println("Manage Services functionality is under development.");
                case 3 -> {
                    System.out.println("Logging out. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewAllUsers() {
        try {
            User[] users = restTemplate.getForObject(baseUrl, User[].class);
            if (users != null) {
                System.out.println("All Registered Users:");
                for (User user : users) {
                    System.out.println(user);
                }
            } else {
                System.out.println("No users found.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while fetching users: " + e.getMessage());
        }
    }
}
