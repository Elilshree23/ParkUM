public class AuthenticationService {


    private final LoginSystem loginSystem;


    public AuthenticationService(){

        UserDatabase database =
                new UserDatabase();


        loginSystem =
                new LoginSystem(
                        database
                );

    }



    public boolean login(
            String username,
            String password
    ){

        return loginSystem.login(
                username,
                password
        );

    }



    public User getCurrentUser(){

        return loginSystem.getCurrentUser();

    }



    public void logout(){

        loginSystem.logout();

    }


}
