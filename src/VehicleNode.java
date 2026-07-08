public class VehicleNode {
    //Data fields in a vehicle
    String licensePlate;
    String ownerName;
    String parkingSlot;
    VehicleNode next;//Pointer to the next vehicle in the list

    public VehicleNode(String licensePlate, String ownerName, String parkingSlot) {
        this.licensePlate = licensePlate;
        this.ownerName = ownerName;
        this.parkingSlot = parkingSlot;
        next = null;
    }
}
