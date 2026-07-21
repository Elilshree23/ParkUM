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

    private HashMap<String, VehicleNode> vehicleMap;
    private BSTNode root;

    public VehicleSearchSystem() {
        vehicleMap = new HashMap<>();
        root = null;
    }

    public void addVehicle(String licensePlate, String ownerName, String parkingSlot) {
        VehicleNode vehicle = new VehicleNode(licensePlate, ownerName, parkingSlot);
        vehicleMap.put(licensePlate, vehicle);
        root = insertIntoBST(root, vehicle);
        System.out.println("Vehicle added to the Optimizer System.");
    }

    private BSTNode insertIntoBST(BSTNode node, VehicleNode vehicle) {
        if (node == null) {
            return new BSTNode(vehicle);
        }

        int comparison = vehicle.getLicensePlate()
                .compareToIgnoreCase(node.getVehicle().getLicensePlate());

        if (comparison < 0) {
            node.setLeft(insertIntoBST(node.getLeft(), vehicle));
        } else if (comparison > 0) {
            node.setRight(insertIntoBST(node.getRight(), vehicle));
        }
        return node;
    }

    public void searchVehicleFast(String licensePlate) {
        VehicleNode found = vehicleMap.get(licensePlate);
        if (found != null) {
            System.out.println("\nVehicle Found!");
            System.out.println("License Plate: " + found.getLicensePlate()
                    + " | Owner: " + found.getOwnerName()
                    + " | Parking Slot: " + found.getParkingSlot());
        } else {
            System.out.println("\nVehicle not found.");
        }
    }

    public void displayVehiclesSorted() {
        System.out.println("\n--- Vehicles Records (Sorted Alphabetically) ---");
        if (root == null) {
            System.out.println("No vehicles in the system.");
        } else {
            inOrderTraversal(root);
        }
    }

    private void inOrderTraversal(BSTNode node) {
        if (node != null) {
            inOrderTraversal(node.getLeft());
            System.out.println("License Plate: " + node.getVehicle().getLicensePlate()
                    + " | Owner: " + node.getVehicle().getOwnerName()
                    + " | Parking Slot: " + node.getVehicle().getParkingSlot());
            inOrderTraversal(node.getRight());
        }
    }

    public void removeVehicle(String licensePlate) {
        if (vehicleMap.containsKey(licensePlate)) {
            vehicleMap.remove(licensePlate);
            root = deleteFromBST(root, licensePlate);
            System.out.println("Vehicle removed from the Optimizer System.");
        } else {
            System.out.println("Vehicle does not exist.");
        }
    }

    private BSTNode deleteFromBST(BSTNode node, String licensePlate) {
        if (node == null) {
            return null;
        }

        int comparison = licensePlate.compareToIgnoreCase(node.getVehicle().getLicensePlate());

        if (comparison < 0) {
            node.setLeft(deleteFromBST(node.getLeft(), licensePlate));
        } else if (comparison > 0) {
            node.setRight(deleteFromBST(node.getRight(), licensePlate));
        } else {
            if (node.getLeft() == null) {
                return node.getRight();
            }
            if (node.getRight() == null) {
                return node.getLeft();
            }

            BSTNode minNode = findMin(node.getRight());
            node.setVehicle(minNode.getVehicle());
            node.setRight(deleteFromBST(node.getRight(), minNode.getVehicle().getLicensePlate()));
        }
        return node;
    }

    private BSTNode findMin(BSTNode node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }
}