package techshop;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationManager {
    private static Map<String, User> users = new HashMap<>();

    // Pre-register some users for testing
    static {
        users.put("admin", new User("admin", "admin123", true));
        users.put("user1", new User("user1", "password1", false));
    }

    public static User authenticateUser(String username, String password) throws AuthenticationException {
        User user = users.get(username);
        if (user == null || !user.authenticate(password)) {
            throw new AuthenticationException("Invalid username or password.");
        }
        return user;
    }

    public static void authorizeAdmin(User user) throws AuthorizationException {
        if (user == null || !user.isAdmin()) {
            throw new AuthorizationException("Access denied. Admin privileges required.");
        }
    }
}

