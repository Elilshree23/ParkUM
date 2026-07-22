public class VehicleNode {

    private final String licensePlate;
    private final String ownerName;
    private final String parkingSlot;

    VehicleNode next;

    public VehicleNode(String licensePlate, String ownerName, String parkingSlot) {

        ValidationUtil.requirePlate(licensePlate);
        ValidationUtil.requireText(ownerName, "Owner name");
        ValidationUtil.requireText(parkingSlot, "Parking slot");

        this.licensePlate = licensePlate.toUpperCase();
        this.ownerName = ownerName;
        this.parkingSlot = parkingSlot;
        this.next = null;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getParkingSlot() {
        return parkingSlot;
    }

    @Override
    public String toString() {
        return "License Plate: " + licensePlate
                + " | Owner: " + ownerName
                + " | Parking Slot: " + parkingSlot;
    }
}
