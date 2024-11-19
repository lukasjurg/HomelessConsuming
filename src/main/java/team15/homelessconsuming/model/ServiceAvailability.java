package team15.homelessconsuming.model;

public class ServiceAvailability {
    private int id;
    private int serviceId;
    private boolean isAvailable;
    private String availableFrom;
    private String availableUntil;

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

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(String availableFrom) {
        this.availableFrom = availableFrom;
    }

    public String getAvailableUntil() {
        return availableUntil;
    }

    public void setAvailableUntil(String availableUntil) {
        this.availableUntil = availableUntil;
    }

    @Override
    public String toString() {
        return "ServiceAvailability{" +
                "id=" + id +
                ", serviceId=" + serviceId +
                ", isAvailable=" + isAvailable +
                ", availableFrom='" + availableFrom + '\'' +
                ", availableUntil='" + availableUntil + '\'' +
                '}';
    }
}