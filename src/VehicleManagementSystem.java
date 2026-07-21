public class VehicleManagementSystem {

    private VehicleNode head;
    private VehicleNode tail;
    private int size;

    public VehicleManagementSystem() {
        head = null;
        tail = null;
        size = 0;
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

        if (getVehicle(normalizedPlate) != null) {

            System.out.println(
                    "Error: Vehicle "
                            + normalizedPlate
                            + " is already registered."
            );

            return false;
        }

        if (isParkingSlotOccupied(normalizedSlot)) {

            System.out.println(
                    "Error: Parking slot "
                            + normalizedSlot
                            + " is already occupied."
            );

            return false;
        }

        VehicleNode newNode =
                new VehicleNode(
                        normalizedPlate,
                        normalizedOwner,
                        normalizedSlot
                );

        if (head == null) {

            head = newNode;
            tail = newNode;

        } else {

            tail.setNext(newNode);
            tail = newNode;
        }

        size++;

        System.out.println(
                "Added: Vehicle "
                        + normalizedPlate
                        + " at "
                        + normalizedSlot
        );

        return true;
    }

    public boolean removeVehicle(
            String licensePlate
    ) {

        if (licensePlate == null
                || licensePlate.isBlank()) {

            System.out.println(
                    "Error: License plate cannot be empty."
            );

            return false;
        }

        String normalizedPlate =
                licensePlate.trim().toUpperCase();

        if (head == null) {

            System.out.println(
                    "System is empty. "
                            + "No vehicles to remove."
            );

            return false;
        }

        if (head.getLicensePlate()
                .equals(normalizedPlate)) {

            head = head.getNext();

            if (head == null) {
                tail = null;
            }

            size--;

            System.out.println(
                    "Removed: Vehicle "
                            + normalizedPlate
            );

            return true;
        }

        VehicleNode previous = head;
        VehicleNode current = head.getNext();

        while (current != null) {

            if (current.getLicensePlate()
                    .equals(normalizedPlate)) {

                previous.setNext(
                        current.getNext()
                );

                if (current == tail) {
                    tail = previous;
                }

                size--;

                System.out.println(
                        "Removed: Vehicle "
                                + normalizedPlate
                );

                return true;
            }

            previous = current;
            current = current.getNext();
        }

        System.out.println(
                "Error: Vehicle "
                        + normalizedPlate
                        + " does not exist."
        );

        return false;
    }

    public void displayVehicles() {

        if (head == null) {

            System.out.println(
                    "System is empty. "
                            + "No vehicles can be displayed."
            );

            return;
        }

        System.out.println(
                "---Current Vehicles Record---"
        );

        VehicleNode current = head;

        while (current != null) {

            System.out.println(
                    "License Plate: "
                            + current.getLicensePlate()
                            + " | Owner: "
                            + current.getOwnerName()
                            + " | Parking Slot: "
                            + current.getParkingSlot()
            );

            current = current.getNext();
        }

        System.out.println();
    }

    public int getSize() {
        return size;
    }

    public VehicleNode getVehicle(
            String licensePlate
    ) {

        if (licensePlate == null
                || licensePlate.isBlank()) {

            return null;
        }

        String normalizedPlate =
                licensePlate.trim().toUpperCase();

        VehicleNode current = head;

        while (current != null) {

            if (current.getLicensePlate()
                    .equals(normalizedPlate)) {

                return current;
            }

            current = current.getNext();
        }

        return null;
    }

    public boolean isParkingSlotOccupied(
            String parkingSlot
    ) {

        if (parkingSlot == null
                || parkingSlot.isBlank()) {

            return false;
        }

        String normalizedSlot =
                parkingSlot.trim().toUpperCase();

        VehicleNode current = head;

        while (current != null) {

            if (current.getParkingSlot()
                    .equals(normalizedSlot)) {

                return true;
            }

            current = current.getNext();
        }

        return false;
    }
}