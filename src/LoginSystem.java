public class LoginSystem {

    private static final int MAX_ATTEMPTS = 3;

    private final UserDatabase database;
    private User currentUser;

    public LoginSystem(UserDatabase database) {

        if (database == null) {
            throw new IllegalArgumentException("User database cannot be null.");
        }

        this.database = database;
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

        User user = database.getUser(username);

        if (user == null) {

            AuditLog.log(username, "LOGIN FAILED (USER NOT FOUND)");
            System.out.println("User does not exist.");

            return false;
        }

        if (user.isAccountLocked()) {

            AuditLog.log(username, "LOGIN BLOCKED (ACCOUNT LOCKED)");
            System.out.println("Account is locked.");

            return false;
        }

        if (database.validatePassword(username, password)) {

            user.resetFailedLoginAttempts();

            currentUser = user;

            AuditLog.log(username, "LOGIN SUCCESS");

            System.out.println("Login successful.");

            return true;
        }

        user.incrementFailedLoginAttempts();

        AuditLog.log(username, "LOGIN FAILED");

        if (user.getFailedLoginAttempts() >= MAX_ATTEMPTS) {

            user.lockAccount();

            AuditLog.log(username, "ACCOUNT LOCKED");

            System.out.println(
                    "Account locked after "
                            + MAX_ATTEMPTS
                            + " failed attempts."
            );

        } else {

            System.out.println(
                    "Wrong password. Remaining attempts: "
                            + (MAX_ATTEMPTS - user.getFailedLoginAttempts())
            );
        }

        return false;
    }

    public void logout() {

        if (currentUser != null) {

            AuditLog.log(currentUser.getUsername(), "LOGOUT");

            currentUser = null;
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public void unlockUser(String username) {

        User user = database.getUser(username);

        if (user != null) {

            user.unlockAccount();

            AuditLog.log(username, "ACCOUNT UNLOCKED");
        }
    }

    public int getMaxAttempts() {
        return MAX_ATTEMPTS;
    }
}