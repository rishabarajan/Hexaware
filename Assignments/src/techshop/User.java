package techshop;

public class User {
    private String username;
    private String password;
    private boolean isAdmin; 

    public User(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}

