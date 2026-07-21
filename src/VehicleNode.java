public class VehicleNode {

    private final String licensePlate;
    private final String ownerName;
    private final String parkingSlot;
    private VehicleNode next;

    public VehicleNode(String licensePlate, String ownerName, String parkingSlot) {
        this.licensePlate = licensePlate;
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

    public VehicleNode getNext() {
        return next;
    }

    public void setNext(VehicleNode next) {
        this.next = next;
    }
}
