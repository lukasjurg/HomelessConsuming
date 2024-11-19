package team15.homelessconsuming.model;

public class VisitLog {
    private int id;
    private int serviceId;
    private int userId;
    private String visitDate;

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    @Override
    public String toString() {
        return "VisitLog{" +
                "id=" + id +
                ", serviceId=" + serviceId +
                ", userId=" + userId +
                ", visitDate='" + visitDate + '\'' +
                '}';
    }
}