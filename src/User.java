public class User {

    private final String username;
    private final String passwordHash;
    private final UserRole role;

    private int failedLoginAttempts;
    private boolean accountLocked;

    public User(String username,
                String passwordHash,
                UserRole role) {

        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;

        this.failedLoginAttempts = 0;
        this.accountLocked = false;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public UserRole getRole() {
        return role;
    }

    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void incrementFailedLoginAttempts() {
        failedLoginAttempts++;
    }

    public void resetFailedLoginAttempts() {
        failedLoginAttempts = 0;
    }

    public void lockAccount() {
        accountLocked = true;
    }

    public void unlockAccount() {
        accountLocked = false;
        failedLoginAttempts = 0;
    }
}