public class AccessControl {



    public static boolean canAddVehicle(
            User user
    ){


        if(user == null){

            return false;

        }



        return user.getRole()
                == UserRole.ADMIN
                ||
                user.getRole()
                        == UserRole.STAFF;

    }





    public static boolean canRemoveVehicle(
            User user
    ){


        if(user == null){

            return false;

        }



        return user.getRole()
                == UserRole.ADMIN;


    }





    public static boolean canViewLogs(
            User user
    ){


        if(user == null){

            return false;

        }



        return user.getRole()
                == UserRole.ADMIN;


    }





}
