public class VehicleService {

    private final VehicleManagementSystem vehicleSystem;
    private final VehicleSearchSystem searchSystem;

    public VehicleService() {

        vehicleSystem = new VehicleManagementSystem();
        searchSystem = new VehicleSearchSystem();
    }

    public boolean addVehicle(
            User user,
            String plate,
            String owner,
            String slot
    ) {

        if (user == null) {
            System.out.println("Please login first.");
            return false;
        }

        if (!AccessControl.canAddVehicle(user)) {

            AuditLog.log(
                    user.getUsername(),
                    "ACCESS DENIED - ADD VEHICLE"
            );

            System.out.println("Access denied.");
            return false;
        }

        boolean success = vehicleSystem.addVehicle(
                plate,
                owner,
                slot
        );

        if (success) {

            searchSystem.addVehicle(
                    plate,
                    owner,
                    slot
            );

            AuditLog.log(
                    user.getUsername(),
                    "ADD VEHICLE : "
                            + plate
            );

        } else {

            AuditLog.log(
                    user.getUsername(),
                    "FAILED TO ADD VEHICLE : "
                            + plate
            );
        }

        return success;
    }

    public boolean removeVehicle(
            User user,
            String plate
    ) {

        if (user == null) {
            System.out.println("Please login first.");
            return false;
        }

        if (!AccessControl.canRemoveVehicle(user)) {

            AuditLog.log(
                    user.getUsername(),
                    "ACCESS DENIED - REMOVE VEHICLE"
            );

            System.out.println("Access denied.");
            return false;
        }

        boolean success =
                vehicleSystem.removeVehicle(
                        plate
                );

        if (success) {

            searchSystem.removeVehicle(
                    plate
            );

            AuditLog.log(
                    user.getUsername(),
                    "REMOVE VEHICLE : "
                            + plate
            );

        } else {

            AuditLog.log(
                    user.getUsername(),
                    "FAILED TO REMOVE VEHICLE : "
                            + plate
            );
        }

        return success;
    }

    public VehicleNode searchVehicle(
            User user,
            String plate
    ) {

        if (user == null) {
            System.out.println("Please login first.");
            return null;
        }

        if (!AccessControl.canSearchVehicle(user)) {

            AuditLog.log(
                    user.getUsername(),
                    "ACCESS DENIED - SEARCH VEHICLE"
            );

            System.out.println("Access denied.");
            return null;
        }

        VehicleNode vehicle =
                searchSystem.searchVehicleFast(
                        plate
                );

        if (vehicle != null) {

            AuditLog.log(
                    user.getUsername(),
                    "SEARCH VEHICLE : "
                            + plate
            );

        } else {

            AuditLog.log(
                    user.getUsername(),
                    "SEARCH FAILED : "
                            + plate
            );
        }

        return vehicle;
    }

    public void displayVehicles(
            User user
    ) {

        if (user == null) {
            System.out.println("Please login first.");
            return;
        }

        AuditLog.log(
                user.getUsername(),
                "VIEW VEHICLE LIST"
        );

        vehicleSystem.displayVehicles();
    }

    public void displayVehiclesSorted(
            User user
    ) {

        if (user == null) {
            System.out.println("Please login first.");
            return;
        }

        AuditLog.log(
                user.getUsername(),
                "VIEW SORTED VEHICLES"
        );

        searchSystem.displayVehiclesSorted();
    }

    public VehicleManagementSystem getVehicleManagementSystem() {
        return vehicleSystem;
    }

    public VehicleSearchSystem getVehicleSearchSystem() {
        return searchSystem;
    }

    public int getVehicleCount() {
        return vehicleSystem.getSize();
    }

    public boolean vehicleExists(
            String plate
    ) {
        return vehicleSystem.getVehicle(plate) != null;
    }

    public boolean isParkingSlotOccupied(
            String slot
    ) {
        return vehicleSystem.isParkingSlotOccupied(slot);
    }
}