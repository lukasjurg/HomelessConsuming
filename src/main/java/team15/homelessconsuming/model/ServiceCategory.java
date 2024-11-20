package team15.homelessconsuming.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceCategory {

    @JsonProperty("category_id") // Matches the backend field name
    private int categoryId;

    @JsonProperty("category_name") // Matches the backend field name
    private String categoryName;

    @JsonProperty("category_description") // Matches the backend field name
    private String categoryDescription;

    // Getters and Setters
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    @Override
    public String toString() {
        return "ServiceCategory{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", categoryDescription='" + categoryDescription + '\'' +
                '}';
    }
}
