import java.util.HashMap;


public class LoginSystem {


    private final UserDatabase database;


    private final HashMap<String,Integer>
            loginAttempts;


    private final HashMap<String,Boolean>
            lockedAccounts;



    private User currentUser;



    private static final int MAX_ATTEMPTS = 3;



    public LoginSystem(
            UserDatabase database
    ){


        this.database = database;


        loginAttempts =
                new HashMap<>();


        lockedAccounts =
                new HashMap<>();

    }





    public boolean login(
            String username,
            String password
    ){


        username =
                username.trim()
                        .toLowerCase();



        if(Boolean.TRUE.equals(
                lockedAccounts.get(username)
        )){


            System.out.println(
                    "Account locked."
            );


            return false;

        }




        if(database.validatePassword(
                username,
                password
        )){


            currentUser =
                    database.getUser(username);



            loginAttempts.put(
                    username,
                    0
            );



            AuditLog.log(
                    username,
                    "LOGIN SUCCESS"
            );



            System.out.println(
                    "Login successful."
            );


            return true;

        }





        int attempts =
                loginAttempts.getOrDefault(
                        username,
                        0
                );


        attempts++;



        loginAttempts.put(
                username,
                attempts
        );



        AuditLog.log(
                username,
                "LOGIN FAILED"
        );



        if(attempts >= MAX_ATTEMPTS){


            lockedAccounts.put(
                    username,
                    true
            );


            System.out.println(
                    "Account locked after 3 attempts."
            );


        }
        else{


            System.out.println(
                    "Wrong password. Attempt "
                            + attempts
            );

        }


        return false;


    }





    public User getCurrentUser(){

        return currentUser;

    }




    public void logout(){


        if(currentUser != null){


            AuditLog.log(
                    currentUser.getUsername(),
                    "LOGOUT"
            );


        }


        currentUser=null;


    }




}
