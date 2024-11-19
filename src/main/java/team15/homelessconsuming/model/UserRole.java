package team15.homelessconsuming.model;

public class UserRole {
    private int id;
    private String roleName;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}