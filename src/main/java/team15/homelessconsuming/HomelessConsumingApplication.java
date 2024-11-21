package team15.homelessconsuming;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import team15.homelessconsuming.model.*;

import java.time.LocalTime;
import java.util.Scanner;

@SpringBootApplication
public class HomelessConsumingApplication implements CommandLineRunner {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "http://localhost:8080/api/users";
    private final String serviceUrl = "http://localhost:8080/api/appservices";

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
            System.out.println("2. Filter services by category");
            System.out.println("3. Filter services by city");
            System.out.println("4. Filter services by working hours");
            System.out.println("5. Go back");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> printAllServices();
                case 2 -> filterServicesByCategory(scanner);
                case 3 -> filterServicesByCity(scanner);
                case 4 -> filterServicesByWorkingHours(scanner);
                case 5 -> {
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
                    System.out.println("Service ID: " + service.getServiceId());
                    System.out.println("Name: " + service.getName());
                    System.out.println("Address: " + service.getAddress());
                    System.out.println("Contact Number: " + service.getContactNumber());
                    System.out.println("Start Time: " + service.getStartTime());
                    System.out.println("End Time: " + service.getEndTime());
                    System.out.println("City: " + (service.getCity() != null ? service.getCity().getCityName() : "N/A"));
                    System.out.println("Category: " + (service.getCategory() != null ? service.getCategory().getCategoryName() : "N/A"));
                    System.out.println("-----------------------------------");
                }
            } else {
                System.out.println("No services are currently available.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while fetching services: " + e.getMessage());
        }
    }

    private void filterServicesByCategory(Scanner scanner) {
        System.out.print("Enter category name: ");
        String category = scanner.nextLine();
        try {
            String url = serviceUrl + "?categoryName=" + category;
            AppService[] services = restTemplate.getForObject(url, AppService[].class);

            if (services != null && services.length > 0) {
                System.out.println("\nFiltered Services by Category:");
                for (AppService service : services) {
                    System.out.println(service);
                }
            } else {
                System.out.println("No services found for the given category.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while filtering services: " + e.getMessage());
        }
    }

    private void filterServicesByCity(Scanner scanner) {
        System.out.print("Enter city name: ");
        String city = scanner.nextLine();
        try {
            String url = serviceUrl + "?cityName=" + city;
            AppService[] services = restTemplate.getForObject(url, AppService[].class);

            if (services != null && services.length > 0) {
                System.out.println("\nFiltered Services by City:");
                for (AppService service : services) {
                    System.out.println(service);
                }
            } else {
                System.out.println("No services found for the given city.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while filtering services: " + e.getMessage());
        }
    }

    private void filterServicesByWorkingHours(Scanner scanner) {
        System.out.print("Enter start time (HH:mm): ");
        String startTime = scanner.nextLine();
        System.out.print("Enter end time (HH:mm): ");
        String endTime = scanner.nextLine();
        try {
            String url = serviceUrl + "?startTime=" + startTime + "&endTime=" + endTime;
            AppService[] services = restTemplate.getForObject(url, AppService[].class);

            if (services != null && services.length > 0) {
                System.out.println("\nFiltered Services by Working Hours:");
                for (AppService service : services) {
                    System.out.println(service);
                }
            } else {
                System.out.println("No services found for the given working hours.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while filtering services: " + e.getMessage());
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
            System.out.println("3. Manage Cities");
            System.out.println("4. Manage Service Categories"); // New option
            System.out.println("5. Log Out");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> viewAllUsers();
                case 2 -> manageServices(scanner);
                case 3 -> manageCities(scanner);
                case 4 -> manageServiceCategories(scanner); // New method
                case 5 -> {
                    System.out.println("Logging out. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void manageServiceCategories(Scanner scanner) {
        while (true) {
            System.out.println("\nManage Service Categories Menu:");
            System.out.println("1. View All Categories");
            System.out.println("2. Create New Category");
            System.out.println("3. Update Existing Category");
            System.out.println("4. Delete Category");
            System.out.println("5. Go Back");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> printAllCategories();
                case 2 -> createCategory(scanner);
                case 3 -> updateCategory(scanner);
                case 4 -> deleteCategory(scanner);
                case 5 -> {
                    System.out.println("Returning to the Admin Menu...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void printAllCategories() {
        try {
            String url = serviceUrl.replace("/appservices", "/servicecategories");
            ServiceCategory[] categories = restTemplate.getForObject(url, ServiceCategory[].class);
            if (categories != null && categories.length > 0) {
                System.out.println("\nAvailable Service Categories:");
                for (ServiceCategory category : categories) {
                    System.out.println("Category ID: " + category.getCategoryId() + ", Name: " + category.getCategoryName() +
                            ", Description: " + category.getCategoryDescription());
                }
            } else {
                System.out.println("No categories found.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while fetching categories: " + e.getMessage());
        }
    }

    private void createCategory(Scanner scanner) {
        try {
            System.out.print("Enter Category Name: ");
            String categoryName = scanner.nextLine();
            System.out.print("Enter Category Description: ");
            String categoryDescription = scanner.nextLine();

            ServiceCategory category = new ServiceCategory();
            category.setCategoryName(categoryName);
            category.setCategoryDescription(categoryDescription);

            String url = serviceUrl.replace("/appservices", "/servicecategories");
            ServiceCategory createdCategory = restTemplate.postForObject(url, category, ServiceCategory.class);
            System.out.println("Category created successfully: " + createdCategory);
        } catch (Exception e) {
            System.out.println("An error occurred while creating the category: " + e.getMessage());
        }
    }

    private void updateCategory(Scanner scanner) {
        try {
            System.out.print("Enter Category ID to Update: ");
            int categoryId = scanner.nextInt();
            scanner.nextLine();

            String fetchUrl = serviceUrl.replace("/appservices", "/servicecategories/") + categoryId;
            ServiceCategory existingCategory = restTemplate.getForObject(fetchUrl, ServiceCategory.class);
            if (existingCategory == null) {
                System.out.println("Category with ID " + categoryId + " not found.");
                return;
            }

            System.out.print("Enter New Category Name (leave blank to keep unchanged): ");
            String categoryName = scanner.nextLine();
            System.out.print("Enter New Category Description (leave blank to keep unchanged): ");
            String categoryDescription = scanner.nextLine();

            if (!categoryName.isBlank()) {
                existingCategory.setCategoryName(categoryName);
            }
            if (!categoryDescription.isBlank()) {
                existingCategory.setCategoryDescription(categoryDescription);
            }

            String updateUrl = serviceUrl.replace("/appservices", "/servicecategories/") + categoryId;
            restTemplate.put(updateUrl, existingCategory);
            System.out.println("Category updated successfully.");
        } catch (Exception e) {
            System.out.println("An error occurred while updating the category: " + e.getMessage());
        }
    }

    private void deleteCategory(Scanner scanner) {
        try {
            System.out.print("Enter Category ID to Delete: ");
            int categoryId = scanner.nextInt();
            scanner.nextLine();

            String deleteUrl = serviceUrl.replace("/appservices", "/servicecategories/") + categoryId;
            restTemplate.delete(deleteUrl);
            System.out.println("Category deleted successfully.");
        } catch (Exception e) {
            System.out.println("An error occurred while deleting the category: " + e.getMessage());
        }
    }


    private void manageServices(Scanner scanner) {
        while (true) {
            System.out.println("\nManage Services Menu:");
            System.out.println("1. View All Services");
            System.out.println("2. Create New Service");
            System.out.println("3. Update Existing Service");
            System.out.println("4. Delete Service");
            System.out.println("5. Go Back");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> printAllServices(); // Reuse existing method
                case 2 -> createService(scanner);
                case 3 -> updateService(scanner);
                case 4 -> deleteService(scanner);
                case 5 -> {
                    System.out.println("Returning to the Admin Menu...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void createService(Scanner scanner) {
        try {
            System.out.println("Enter Service Name: ");
            String name = scanner.nextLine();
            System.out.println("Enter Service Address: ");
            String address = scanner.nextLine();
            System.out.println("Enter Contact Number: ");
            String contact = scanner.nextLine();
            System.out.println("Enter Start Time (HH:mm): ");
            String startTime = scanner.nextLine();
            System.out.println("Enter End Time (HH:mm): ");
            String endTime = scanner.nextLine();
            System.out.println("Enter City ID: ");
            int cityId = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Enter Category ID: ");
            int categoryId = scanner.nextInt();
            scanner.nextLine();

            City city = new City();
            city.setCityId(cityId);
            ServiceCategory category = new ServiceCategory();
            category.setCategoryId(categoryId);
            AppService service = new AppService();
            service.setName(name);
            service.setAddress(address);
            service.setContactNumber(contact);
            service.setStartTime(LocalTime.parse(startTime));
            service.setEndTime(LocalTime.parse(endTime));
            service.setCity(city);
            service.setCategory(category);

            String url = serviceUrl;
            AppService createdService = restTemplate.postForObject(url, service, AppService.class);
            System.out.println("Service created successfully: " + createdService);
        } catch (Exception e) {
            System.out.println("An error occurred while creating the service: " + e.getMessage());
        }
    }

    private void updateService(Scanner scanner) {
        try {
            System.out.println("Enter Service ID to Update: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            // Fetch existing service details
            String fetchUrl = serviceUrl + "/" + id;
            AppService existingService = restTemplate.getForObject(fetchUrl, AppService.class);
            if (existingService == null) {
                System.out.println("Service with ID " + id + " not found.");
                return;
            }

            System.out.println("Enter New Service Name (leave blank to keep unchanged): ");
            String name = scanner.nextLine();
            System.out.println("Enter New Service Address (leave blank to keep unchanged): ");
            String address = scanner.nextLine();
            System.out.println("Enter New Contact Number (leave blank to keep unchanged): ");
            String contact = scanner.nextLine();
            System.out.println("Enter New Start Time (HH:mm) (leave blank to keep unchanged): ");
            String startTime = scanner.nextLine();
            System.out.println("Enter New End Time (HH:mm) (leave blank to keep unchanged): ");
            String endTime = scanner.nextLine();
            System.out.println("Enter New Category ID (leave blank to keep unchanged): ");
            String categoryIdInput = scanner.nextLine();

            // Update only non-blank fields
            if (!name.isBlank()) existingService.setName(name);
            if (!address.isBlank()) existingService.setAddress(address);
            if (!contact.isBlank()) existingService.setContactNumber(contact);
            if (!startTime.isBlank()) existingService.setStartTime(LocalTime.parse(startTime));
            if (!endTime.isBlank()) existingService.setEndTime(LocalTime.parse(endTime));
            if (!categoryIdInput.isBlank()) {
                int categoryId = Integer.parseInt(categoryIdInput);
                ServiceCategory category = new ServiceCategory();
                category.setCategoryId(categoryId);
                existingService.setCategory(category);
            }

            // Send updated service to the backend
            String updateUrl = serviceUrl + "/" + id;
            restTemplate.put(updateUrl, existingService);
            System.out.println("Service updated successfully.");
        } catch (HttpClientErrorException e) {
            System.out.println("Failed to update service: " + e.getStatusCode() + " : " + e.getResponseBodyAsString());
        } catch (Exception e) {
            System.out.println("An error occurred while updating the service: " + e.getMessage());
        }
    }


    private void deleteService(Scanner scanner) {
        try {
            System.out.println("Enter Service ID to Delete: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            String url = serviceUrl + "/" + id;
            restTemplate.delete(url);
            System.out.println("Service deleted successfully.");
        } catch (Exception e) {
            System.out.println("An error occurred while deleting the service: " + e.getMessage());
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

    private void manageCities(Scanner scanner) {
        while (true) {
            System.out.println("\nManage Cities Menu:");
            System.out.println("1. View All Cities");
            System.out.println("2. Create New City");
            System.out.println("3. Update Existing City");
            System.out.println("4. Delete City");
            System.out.println("5. Go Back");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> printAllCities();
                case 2 -> createCity(scanner);
                case 3 -> updateCity(scanner);
                case 4 -> deleteCity(scanner);
                case 5 -> {
                    System.out.println("Returning to the Admin Menu...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void createCity(Scanner scanner) {
        try {
            System.out.print("Enter City Name: ");
            String cityName = scanner.nextLine();

            City city = new City();
            city.setCityName(cityName);

            String url = serviceUrl.replace("/appservices", "/cities");
            City createdCity = restTemplate.postForObject(url, city, City.class);
            System.out.println("City created successfully: " + createdCity);
        } catch (Exception e) {
            System.out.println("An error occurred while creating the city: " + e.getMessage());
        }
    }

    private void updateCity(Scanner scanner) {
        try {
            System.out.print("Enter City ID to Update: ");
            int cityId = scanner.nextInt();
            scanner.nextLine();

            String fetchUrl = serviceUrl.replace("/appservices", "/cities/") + cityId;
            City existingCity = restTemplate.getForObject(fetchUrl, City.class);
            if (existingCity == null) {
                System.out.println("City with ID " + cityId + " not found.");
                return;
            }

            System.out.print("Enter New City Name (leave blank to keep unchanged): ");
            String cityName = scanner.nextLine();
            if (!cityName.isBlank()) {
                existingCity.setCityName(cityName);
            }

            String updateUrl = serviceUrl.replace("/appservices", "/cities/") + cityId;
            restTemplate.put(updateUrl, existingCity);
            System.out.println("City updated successfully.");
        } catch (Exception e) {
            System.out.println("An error occurred while updating the city: " + e.getMessage());
        }
    }

    private void deleteCity(Scanner scanner) {
        try {
            System.out.print("Enter City ID to Delete: ");
            int cityId = scanner.nextInt();
            scanner.nextLine();

            String deleteUrl = serviceUrl.replace("/appservices", "/cities/") + cityId;
            restTemplate.delete(deleteUrl);
            System.out.println("City deleted successfully.");
        } catch (Exception e) {
            System.out.println("An error occurred while deleting the city: " + e.getMessage());
        }
    }

    private void printAllCities() {
        try {
            City[] cities = restTemplate.getForObject(serviceUrl.replace("/appservices", "/cities"), City[].class);
            if (cities != null && cities.length > 0) {
                System.out.println("\nAvailable Cities:");
                for (City city : cities) {
                    System.out.println("City ID: " + city.getCityId() + ", Name: " + city.getCityName());
                }
            } else {
                System.out.println("No cities found.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while fetching cities: " + e.getMessage());
        }
    }
}
