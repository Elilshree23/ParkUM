import java.util.HashMap;

class BSTNode {

    VehicleNode vehicle;
    BSTNode left;
    BSTNode right;

    BSTNode(VehicleNode vehicle) {
        this.vehicle = vehicle;
    }
}

public class VehicleSearchSystem {

    private final HashMap<String, VehicleNode> vehicleMap;
    private BSTNode root;

    public VehicleSearchSystem() {
        vehicleMap = new HashMap<>();
    }

    public boolean addVehicle(String plate, String owner, String slot) {

        ValidationUtil.requirePlate(plate);

        plate = plate.toUpperCase();

        if (vehicleMap.containsKey(plate)) {
            System.out.println("Vehicle already exists.");
            return false;
        }

        VehicleNode vehicle = new VehicleNode(plate, owner, slot);

        vehicleMap.put(plate, vehicle);
        root = insert(root, vehicle);

        System.out.println("Vehicle added to Optimizer.");

        return true;
    }

    private BSTNode insert(BSTNode node, VehicleNode vehicle) {

        if (node == null)
            return new BSTNode(vehicle);

        if (vehicle.getLicensePlate().compareToIgnoreCase(node.vehicle.getLicensePlate()) < 0)
            node.left = insert(node.left, vehicle);

        else if (vehicle.getLicensePlate().compareToIgnoreCase(node.vehicle.getLicensePlate()) > 0)
            node.right = insert(node.right, vehicle);

        return node;
    }

    public VehicleNode searchVehicleFast(String plate) {

        ValidationUtil.requirePlate(plate);

        VehicleNode vehicle = vehicleMap.get(plate.toUpperCase());

        if (vehicle == null) {
            System.out.println("Vehicle not found.");
            return null;
        }

        System.out.println(vehicle);

        return vehicle;
    }

    public boolean contains(String plate) {
        return vehicleMap.containsKey(plate.toUpperCase());
    }

    public int size() {
        return vehicleMap.size();
    }

    public void displayVehiclesSorted() {

        System.out.println("\n------ Sorted Vehicles ------");

        if (root == null)
            System.out.println("No vehicles.");

        else
            inOrder(root);

        System.out.println("-----------------------------");
    }

    private void inOrder(BSTNode node) {

        if (node == null)
            return;

        inOrder(node.left);
        System.out.println(node.vehicle);
        inOrder(node.right);
    }

    public void removeVehicle(String plate) {

        ValidationUtil.requirePlate(plate);

        plate = plate.toUpperCase();

        if (!vehicleMap.containsKey(plate)) {
            System.out.println("Vehicle not found.");
            return;
        }

        vehicleMap.remove(plate);
        root = delete(root, plate);

        System.out.println("Vehicle removed.");
    }

    private BSTNode delete(BSTNode node, String plate) {

        if (node == null)
            return null;

        int cmp = plate.compareToIgnoreCase(node.vehicle.getLicensePlate());

        if (cmp < 0)
            node.left = delete(node.left, plate);

        else if (cmp > 0)
            node.right = delete(node.right, plate);

        else {

            if (node.left == null)
                return node.right;

            if (node.right == null)
                return node.left;

            BSTNode min = findMin(node.right);

            node.vehicle = min.vehicle;
            node.right = delete(node.right, min.vehicle.getLicensePlate());
        }

        return node;
    }

    private BSTNode findMin(BSTNode node) {

        while (node.left != null)
            node = node.left;

        return node;
    }
}