import java.util.HashMap;


public class UserDatabase {


    private final HashMap<String, User> users;



    public UserDatabase(){

        users = new HashMap<>();


        // Default admin account
        addUser(
                "admin",
                "admin123",
                UserRole.ADMIN
        );


        // Default staff account
        addUser(
                "staff",
                "staff123",
                UserRole.STAFF
        );

    }




    public boolean addUser(
            String username,
            String password,
            UserRole role
    ){


        if(username == null ||
                username.isBlank()){

            return false;

        }


        if(password == null ||
                password.isBlank()){

            return false;

        }



        String normalized =
                username.trim()
                        .toLowerCase();



        if(users.containsKey(normalized)){


            System.out.println(
                    "Username already exists."
            );


            return false;

        }



        String hashedPassword =
                PasswordHasher.hashPassword(
                        password
                );



        User user =
                new User(
                        normalized,
                        hashedPassword,
                        role
                );



        users.put(
                normalized,
                user
        );



        return true;

    }




    public User getUser(
            String username
    ){


        if(username == null){

            return null;

        }



        return users.get(
                username.trim()
                        .toLowerCase()
        );

    }




    public boolean validatePassword(
            String username,
            String password
    ){


        User user =
                getUser(username);



        if(user == null){

            return false;

        }



        String hashed =
                PasswordHasher.hashPassword(
                        password
                );



        return hashed.equals(
                user.getPasswordHash()
        );

    }



}
