public class AuthenticationService {

    private final UserDatabase database;
    private final LoginSystem loginSystem;

    public AuthenticationService() {

        database = new UserDatabase();
        loginSystem = new LoginSystem(database);
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

        return loginSystem.login(username, password);
    }

    public void logout() {
        loginSystem.logout();
    }

    public User getCurrentUser() {
        return loginSystem.getCurrentUser();
    }

    public UserDatabase getUserDatabase() {
        return database;
    }

    public boolean registerUser(String username,
                                String password,
                                UserRole role) {

        return database.addUser(username, password, role);
    }

    public boolean removeUser(String username) {
        return database.removeUser(username);
    }
}
