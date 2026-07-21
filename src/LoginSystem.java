import java.util.HashMap;

public class LoginSystem {

    private final UserDatabase database;

    private final HashMap<String, Integer> loginAttempts;
    private final HashMap<String, Boolean> lockedAccounts;

    private User currentUser;

    private static final int MAX_ATTEMPTS = 3;

    public LoginSystem(UserDatabase database) {

        this.database = database;

        loginAttempts = new HashMap<>();
        lockedAccounts = new HashMap<>();
    }

    public boolean login(String username, String password) {

        if (username == null || username.isBlank()) {
            System.out.println("Username cannot be empty.");
            return false;
        }

        if (password == null || password.isBlank()) {
            System.out.println("Password cannot be empty.");
            return false;
        }

        username = username.trim().toLowerCase();

        if (Boolean.TRUE.equals(lockedAccounts.get(username))) {

            AuditLog.log(username, "LOGIN BLOCKED (ACCOUNT LOCKED)");
            System.out.println("Account is locked.");
            return false;
        }

        if (!database.userExists(username)) {

            AuditLog.log(username, "LOGIN FAILED (USER NOT FOUND)");
            System.out.println("User does not exist.");
            return false;
        }

        if (database.validatePassword(username, password)) {

            currentUser = database.getUser(username);

            loginAttempts.remove(username);

            AuditLog.log(username, "LOGIN SUCCESS");

            System.out.println("Login successful.");

            return true;
        }

        int attempts = loginAttempts.getOrDefault(username, 0) + 1;

        loginAttempts.put(username, attempts);

        AuditLog.log(username, "LOGIN FAILED");

        if (attempts >= MAX_ATTEMPTS) {

            lockedAccounts.put(username, true);

            AuditLog.log(username, "ACCOUNT LOCKED");

            System.out.println("Account locked after "
                    + MAX_ATTEMPTS
                    + " failed attempts.");

        } else {

            System.out.println("Wrong password. Remaining attempts: "
                    + (MAX_ATTEMPTS - attempts));
        }

        return false;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public void logout() {

        if (currentUser != null) {

            AuditLog.log(
                    currentUser.getUsername(),
                    "LOGOUT"
            );

            currentUser = null;
        }
    }
}