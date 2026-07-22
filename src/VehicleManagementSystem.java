public class VehicleManagementSystem {

    private VehicleNode head;
    private VehicleNode tail;
    private int size;

    public VehicleManagementSystem() {
        head = null;
        tail = null;
        size = 0;
    }

    public boolean addVehicle(String plate, String owner, String slot) {

        ValidationUtil.requirePlate(plate);

        if (getVehicle(plate) != null) {
            System.out.println("Vehicle already exists.");
            return false;
        }

        VehicleNode newNode = new VehicleNode(plate, owner, slot);

        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }

        size++;
        System.out.println("Added : " + plate);

        return true;
    }

    public boolean removeVehicle(String plate) {

        ValidationUtil.requirePlate(plate);

        if (head == null) {
            System.out.println("No vehicles found.");
            return false;
        }

        if (head.getLicensePlate().equalsIgnoreCase(plate)) {

            head = head.next;

            if (head == null)
                tail = null;

            size--;
            System.out.println("Removed : " + plate);
            return true;
        }

        VehicleNode previous = head;
        VehicleNode current = head.next;

        while (current != null) {

            if (current.getLicensePlate().equalsIgnoreCase(plate)) {

                previous.next = current.next;

                if (current == tail)
                    tail = previous;

                size--;

                System.out.println("Removed : " + plate);
                return true;
            }

            previous = current;
            current = current.next;
        }

        System.out.println("Vehicle not found.");
        return false;
    }

    public VehicleNode getVehicle(String plate) {

        VehicleNode current = head;

        while (current != null) {

            if (current.getLicensePlate().equalsIgnoreCase(plate))
                return current;

            current = current.next;
        }

        return null;
    }

    public void displayVehicles() {

        if (head == null) {
            System.out.println("No vehicles available.");
            return;
        }

        System.out.println("\n------ Vehicle List ------");

        VehicleNode current = head;

        while (current != null) {
            System.out.println(current);
            current = current.next;
        }

        System.out.println("--------------------------");
    }

    public boolean contains(String plate) {
        return getVehicle(plate) != null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }
}