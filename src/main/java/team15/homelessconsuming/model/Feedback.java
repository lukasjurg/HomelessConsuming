package team15.homelessconsuming.model;

public class Feedback {
    private int id;
    private int serviceId;
    private String feedbackText;
    private int rating;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", serviceId=" + serviceId +
                ", feedbackText='" + feedbackText + '\'' +
                ", rating=" + rating +
                '}';
    }
}