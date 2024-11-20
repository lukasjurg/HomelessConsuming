package team15.homelessconsuming.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceCategory {

    @JsonProperty("category_ID")
    private int id; // Corresponds to category_ID from the backend

    @JsonProperty("category_name")
    private String name; // Corresponds to category_name from the backend

    @JsonProperty("category_description")
    private String description; // Corresponds to category_description from the backend

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ServiceCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
