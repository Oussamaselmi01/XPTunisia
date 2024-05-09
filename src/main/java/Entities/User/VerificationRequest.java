package Entities.User;

public class VerificationRequest {
    // Attributes
    private int id;
    private User user;
    private boolean confirmed;

    // Constructors
    public VerificationRequest() {
    }

    public VerificationRequest(int id ,User user, boolean confirmed) {
        this.id = id;
        this.user = user;
        this.confirmed = confirmed;
    }

    public VerificationRequest(User user, boolean confirmed) {
        this.user = user;
        this.confirmed = confirmed;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    // toString method
    @Override
    public String toString() {
        return "VerificationRequest{" +
                "id=" + id +
                ", user=" + user +
                ", confirmed=" + confirmed +
                '}';
    }
}
