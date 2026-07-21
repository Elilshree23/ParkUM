import java.time.LocalDateTime;

public class Action {

    private final ActionType actionType;

    private final String username;

    private final String licensePlate;

    private final String ownerName;

    private final String parkingSlot;

    private final String description;

    private final LocalDateTime timestamp;

    public Action(

            ActionType actionType,

            String username,

            String licensePlate,

            String ownerName,

            String parkingSlot,

            String description

    ) {

        this.actionType = actionType;

        this.username = username;

        this.licensePlate = licensePlate;

        this.ownerName = ownerName;

        this.parkingSlot = parkingSlot;

        this.description = description;

        this.timestamp = LocalDateTime.now();
    }

    public ActionType getActionType() {

        return actionType;
    }

    public String getUsername() {

        return username;
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

    public String getDescription() {

        return description;
    }

    public LocalDateTime getTimestamp() {

        return timestamp;
    }

}