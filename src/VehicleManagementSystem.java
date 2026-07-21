public class VehicleManagementSystem {

    private VehicleNode head;
    private VehicleNode tail;
    private int size;

    public VehicleManagementSystem() {
        head = null;
        tail = null;
        size = 0;
    }

    // Add operation: Adding a new vehicle record to the back of the list
    public void addVehicle(String licensePlate, String ownerName, String parkingSlot) {
        VehicleNode newNode = new VehicleNode(licensePlate, ownerName, parkingSlot);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }

        size++;
        System.out.println("Added: Vehicle " + licensePlate + " at " + parkingSlot);
    }

    // Remove operation: Finds and removes a vehicle by its license plate
    public boolean removeVehicle(String licensePlate) {
        if (head == null) {
            System.out.println("System is empty. No vehicles to remove.");
            return false;
        }

        if (head.getLicensePlate().equalsIgnoreCase(licensePlate)) {
            head = head.getNext();
            if (head == null) {
                tail = null;
            }
            size--;
            System.out.println("Removed: Vehicle " + licensePlate);
            return true;
        }

        VehicleNode current = head.getNext();
        VehicleNode previous = head;

        while (current != null) {
            if (current.getLicensePlate().equalsIgnoreCase(licensePlate)) {
                break;
            }
            previous = current;
            current = current.getNext();
        }

        if (current == null) {
            System.out.println("Error: Vehicle " + licensePlate + " does not exist.");
            return false;
        }

        previous.setNext(current.getNext());
        if (current == tail) {
            tail = previous;
        }

        size--;
        System.out.println("Removed: Vehicle " + licensePlate);
        return true;
    }

    // Display operation: Prints all the currently parked vehicles record
    public void displayVehicles() {
        if (head == null) {
            System.out.println("System is empty. No vehicles can be displayed.");
            return;
        }

        System.out.println("---Current Vehicles Record---");
        VehicleNode current = head;
        while (current != null) {
            System.out.println("License Plate: " + current.getLicensePlate()
                    + " | Owner: " + current.getOwnerName()
                    + " | Parking Slot: " + current.getParkingSlot());
            current = current.getNext();
        }
        System.out.println();
    }

    public int getSize() {
        return size;
    }

    public VehicleNode getVehicle(String licensePlate) {
        VehicleNode current = head;
        while (current != null) {
            if (current.getLicensePlate().equalsIgnoreCase(licensePlate)) {
                return current;
            }
            current = current.getNext();
        }
        return null;
    }
}