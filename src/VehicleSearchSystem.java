import java.util.HashMap;

class BSTNode {

    private VehicleNode vehicle;
    private BSTNode left;
    private BSTNode right;

    public BSTNode(VehicleNode vehicle) {
        this.vehicle = vehicle;
        this.left = null;
        this.right = null;
    }

    public VehicleNode getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleNode vehicle) {
        this.vehicle = vehicle;
    }

    public BSTNode getLeft() {
        return left;
    }

    public void setLeft(BSTNode left) {
        this.left = left;
    }

    public BSTNode getRight() {
        return right;
    }

    public void setRight(BSTNode right) {
        this.right = right;
    }
}

public class VehicleSearchSystem {

    private final HashMap<String, VehicleNode> vehicleMap;
    private BSTNode root;

    public VehicleSearchSystem() {
        vehicleMap = new HashMap<>();
        root = null;
    }

    public boolean addVehicle(
            String licensePlate,
            String ownerName,
            String parkingSlot
    ) {

        if (licensePlate == null
                || licensePlate.isBlank()) {

            System.out.println(
                    "Error: License plate cannot be empty."
            );

            return false;
        }

        if (ownerName == null
                || ownerName.isBlank()) {

            System.out.println(
                    "Error: Owner name cannot be empty."
            );

            return false;
        }

        if (parkingSlot == null
                || parkingSlot.isBlank()) {

            System.out.println(
                    "Error: Parking slot cannot be empty."
            );

            return false;
        }

        String normalizedPlate =
                licensePlate.trim().toUpperCase();

        String normalizedOwner =
                ownerName.trim();

        String normalizedSlot =
                parkingSlot.trim().toUpperCase();

        if (vehicleMap.containsKey(normalizedPlate)) {

            System.out.println(
                    "Error: Vehicle "
                            + normalizedPlate
                            + " already exists."
            );

            return false;
        }

        VehicleNode vehicle =
                new VehicleNode(
                        normalizedPlate,
                        normalizedOwner,
                        normalizedSlot
                );

        vehicleMap.put(
                normalizedPlate,
                vehicle
        );

        root =
                insertIntoBST(
                        root,
                        vehicle
                );

        System.out.println(
                "Vehicle added to the "
                        + "Optimizer System."
        );

        return true;
    }

    private BSTNode insertIntoBST(
            BSTNode node,
            VehicleNode vehicle
    ) {

        if (node == null) {
            return new BSTNode(vehicle);
        }

        int comparison =
                vehicle.getLicensePlate()
                        .compareTo(
                                node.getVehicle()
                                        .getLicensePlate()
                        );

        if (comparison < 0) {

            node.setLeft(
                    insertIntoBST(
                            node.getLeft(),
                            vehicle
                    )
            );

        } else if (comparison > 0) {

            node.setRight(
                    insertIntoBST(
                            node.getRight(),
                            vehicle
                    )
            );
        }

        return node;
    }

    public VehicleNode searchVehicleFast(
            String licensePlate
    ) {

        if (licensePlate == null
                || licensePlate.isBlank()) {

            System.out.println(
                    "\nError: License plate "
                            + "cannot be empty."
            );

            return null;
        }

        String normalizedPlate =
                licensePlate.trim().toUpperCase();

        VehicleNode found =
                vehicleMap.get(normalizedPlate);

        if (found != null) {

            System.out.println(
                    "\nVehicle Found!"
            );

            System.out.println(
                    "License Plate: "
                            + found.getLicensePlate()
                            + " | Owner: "
                            + found.getOwnerName()
                            + " | Parking Slot: "
                            + found.getParkingSlot()
            );

        } else {

            System.out.println(
                    "\nVehicle not found."
            );
        }

        return found;
    }

    public void displayVehiclesSorted() {

        System.out.println(
                "\n--- Vehicles Records "
                        + "(Sorted Alphabetically) ---"
        );

        if (root == null) {

            System.out.println(
                    "No vehicles in the system."
            );

        } else {

            inOrderTraversal(root);
        }
    }

    private void inOrderTraversal(
            BSTNode node
    ) {

        if (node != null) {

            inOrderTraversal(
                    node.getLeft()
            );

            System.out.println(
                    "License Plate: "
                            + node.getVehicle()
                            .getLicensePlate()
                            + " | Owner: "
                            + node.getVehicle()
                            .getOwnerName()
                            + " | Parking Slot: "
                            + node.getVehicle()
                            .getParkingSlot()
            );

            inOrderTraversal(
                    node.getRight()
            );
        }
    }

    public boolean removeVehicle(
            String licensePlate
    ) {

        if (licensePlate == null
                || licensePlate.isBlank()) {

            System.out.println(
                    "Error: License plate "
                            + "cannot be empty."
            );

            return false;
        }

        String normalizedPlate =
                licensePlate.trim().toUpperCase();

        if (!vehicleMap.containsKey(
                normalizedPlate
        )) {

            System.out.println(
                    "Vehicle does not exist."
            );

            return false;
        }

        vehicleMap.remove(
                normalizedPlate
        );

        root =
                deleteFromBST(
                        root,
                        normalizedPlate
                );

        System.out.println(
                "Vehicle removed from the "
                        + "Optimizer System."
        );

        return true;
    }

    private BSTNode deleteFromBST(
            BSTNode node,
            String licensePlate
    ) {

        if (node == null) {
            return null;
        }

        int comparison =
                licensePlate.compareTo(
                        node.getVehicle()
                                .getLicensePlate()
                );

        if (comparison < 0) {

            node.setLeft(
                    deleteFromBST(
                            node.getLeft(),
                            licensePlate
                    )
            );

        } else if (comparison > 0) {

            node.setRight(
                    deleteFromBST(
                            node.getRight(),
                            licensePlate
                    )
            );

        } else {

            if (node.getLeft() == null) {
                return node.getRight();
            }

            if (node.getRight() == null) {
                return node.getLeft();
            }

            BSTNode minNode =
                    findMin(
                            node.getRight()
                    );

            node.setVehicle(
                    minNode.getVehicle()
            );

            node.setRight(
                    deleteFromBST(
                            node.getRight(),
                            minNode.getVehicle()
                                    .getLicensePlate()
                    )
            );
        }

        return node;
    }

    private BSTNode findMin(
            BSTNode node
    ) {

        while (node.getLeft() != null) {
            node = node.getLeft();
        }

        return node;
    }
}