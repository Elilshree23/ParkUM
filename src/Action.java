public class Action {

    private final String actionType;
    private final String licensePlate;
    private final String ownerName;
    private final String parkingSlot;

    public Action(String actionType, String licensePlate, String ownerName, String parkingSlot) {
        this.actionType = actionType;
        this.licensePlate = licensePlate;
        this.ownerName = ownerName;
        this.parkingSlot = parkingSlot;
    }

    public String getActionType() {
        return actionType;
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
}
