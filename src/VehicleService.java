public class VehicleService {


    private final VehicleManagementSystem vehicleSystem;



    public VehicleService(){

        vehicleSystem =
                new VehicleManagementSystem();

    }





    public boolean addVehicle(
            User user,
            String plate,
            String owner,
            String slot
    ){


        if(!AccessControl.canAddVehicle(user)){

            System.out.println(
                    "Access denied."
            );

            return false;

        }



        AuditLog.log(
                user.getUsername(),
                "ADD VEHICLE "
                        + plate
        );



        return vehicleSystem.addVehicle(
                plate,
                owner,
                slot
        );

    }





    public boolean removeVehicle(
            User user,
            String plate
    ){



        if(!AccessControl.canRemoveVehicle(user)){

            return false;

        }



        AuditLog.log(
                user.getUsername(),
                "REMOVE VEHICLE "
                        + plate
        );



        return vehicleSystem.removeVehicle(
                plate
        );

    }





    public VehicleNode search(
            String plate
    ){

        return vehicleSystem.getVehicle(
                plate
        );

    }



    public void display(){

        vehicleSystem.displayVehicles();

    }



}
