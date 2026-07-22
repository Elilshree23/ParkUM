public class Action {

    private final ActionType actionType;
    private final String licensePlate;
    private final String ownerName;
    private final String parkingSlot;

    public Action(ActionType actionType,
                  String licensePlate,
                  String ownerName,
                  String parkingSlot) {

        this.actionType = actionType;
        this.licensePlate = licensePlate;
        this.ownerName = ownerName;
        this.parkingSlot = parkingSlot;
    }

    public ActionType getActionType() {
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