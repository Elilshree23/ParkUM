import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class UserDatabase {

    private final HashMap<String, User> users;

    public UserDatabase() {

        users = new HashMap<>();

        addUser("admin", "admin123", UserRole.ADMIN);
        addUser("staff", "staff123", UserRole.STAFF);
    }

    public boolean addUser(String username,
                           String password,
                           UserRole role) {

        if (username == null || username.isBlank()) {
            return false;
        }

        if (password == null || password.isBlank()) {
            return false;
        }

        if (role == null) {
            return false;
        }

        String normalized = username.trim().toLowerCase();

        if (users.containsKey(normalized)) {
            System.out.println("Username already exists.");
            return false;
        }

        User user = new User(
                normalized,
                PasswordHasher.hashPassword(password),
                role
        );

        users.put(normalized, user);

        return true;
    }

    public User getUser(String username) {

        if (username == null || username.isBlank()) {
            return null;
        }

        return users.get(username.trim().toLowerCase());
    }

    public boolean validatePassword(String username,
                                    String password) {

        User user = getUser(username);

        if (user == null) {
            return false;
        }

        return PasswordHasher.verifyPassword(
                password,
                user.getPasswordHash()
        );
    }

    public boolean removeUser(String username) {

        if (username == null || username.isBlank()) {
            return false;
        }

        return users.remove(username.trim().toLowerCase()) != null;
    }

    public boolean userExists(String username) {

        if (username == null || username.isBlank()) {
            return false;
        }

        return users.containsKey(username.trim().toLowerCase());
    }

    public Collection<User> getAllUsers() {
        return Collections.unmodifiableCollection(users.values());
    }

    public int getTotalUsers() {
        return users.size();
    }
}