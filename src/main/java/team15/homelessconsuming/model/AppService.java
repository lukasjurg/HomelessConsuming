package team15.homelessconsuming.model;

import java.time.LocalTime;

public class AppService {

    private int id; // Matches service_ID from the backend
    private String name;
    private String address;
    private String contactNumber;
    private LocalTime startTime;
    private LocalTime endTime;
    private City city;
    private ServiceCategory category;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public ServiceCategory getCategory() {
        return category;
    }

    public void setCategory(ServiceCategory category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "AppService{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", city=" + (city != null ? city.getName() : "N/A") +
                ", category=" + (category != null ? category.getName() : "N/A") +
                '}';
    }
}
