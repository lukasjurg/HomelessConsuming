package team15.homelessconsuming.model;

public class City {

    private int id; // Matches city_ID from the backend
    private String name; // Matches city_name from the backend

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
}
