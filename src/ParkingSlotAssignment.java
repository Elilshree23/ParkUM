import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

class ParkingSlot
        implements Comparable<ParkingSlot> {

    private final String slotId;
    private final int distance;
    private boolean available;

    public ParkingSlot(
            String slotId,
            int distance
    ) {

        if (slotId == null
                || slotId.isBlank()) {

            throw new IllegalArgumentException(
                    "Slot ID cannot be null or empty."
            );
        }

        if (distance < 0) {

            throw new IllegalArgumentException(
                    "Distance cannot be negative."
            );
        }

        this.slotId =
                slotId.trim().toUpperCase();

        this.distance =
                distance;

        this.available =
                true;
    }

    public String getSlotId() {
        return slotId;
    }

    public int getDistance() {
        return distance;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(
            boolean available
    ) {
        this.available = available;
    }

    @Override
    public int compareTo(
            ParkingSlot other
    ) {

        return Integer.compare(
                this.distance,
                other.distance
        );
    }

    @Override
    public String toString() {

        return "Slot "
                + slotId
                + " (Distance: "
                + distance
                + "m)";
    }
}

public class ParkingSlotAssignment {

    private final PriorityQueue<ParkingSlot>
            availableSlots;

    private final Set<String>
            allSlotIds;

    private final Set<String>
            availableSlotIds;

    public ParkingSlotAssignment() {

        availableSlots =
                new PriorityQueue<>();

        allSlotIds =
                new HashSet<>();

        availableSlotIds =
                new HashSet<>();
    }

    public boolean addParkingSlot(
            ParkingSlot slot
    ) {

        if (slot == null) {

            System.out.println(
                    "Error: Cannot add a null slot."
            );

            return false;
        }

        if (allSlotIds.contains(
                slot.getSlotId()
        )) {

            System.out.println(
                    "Error: Slot "
                            + slot.getSlotId()
                            + " is already registered."
            );

            return false;
        }

        allSlotIds.add(
                slot.getSlotId()
        );

        availableSlots.add(
                slot
        );

        availableSlotIds.add(
                slot.getSlotId()
        );

        System.out.println(
                "Slot "
                        + slot.getSlotId()
                        + " added to the parking pool."
        );

        return true;
    }

    public ParkingSlot assignSlot() {

        if (availableSlots.isEmpty()) {

            System.out.println(
                    "Assignment Failed: "
                            + "No available parking slots!"
            );

            return null;
        }

        ParkingSlot assignedSlot =
                availableSlots.poll();

        availableSlotIds.remove(
                assignedSlot.getSlotId()
        );

        assignedSlot.setAvailable(
                false
        );

        System.out.println(
                "Successfully Assigned: "
                        + assignedSlot
        );

        return assignedSlot;
    }

    public boolean releaseSlot(
            ParkingSlot slot
    ) {

        if (slot == null) {

            System.out.println(
                    "Error: Cannot release "
                            + "a null slot."
            );

            return false;
        }

        if (!allSlotIds.contains(
                slot.getSlotId()
        )) {

            System.out.println(
                    "Error: Slot "
                            + slot.getSlotId()
                            + " was never registered."
            );

            return false;
        }

        if (availableSlotIds.contains(
                slot.getSlotId()
        )) {

            System.out.println(
                    "Error: Slot "
                            + slot.getSlotId()
                            + " is already available."
            );

            return false;
        }

        slot.setAvailable(
                true
        );

        availableSlots.add(
                slot
        );

        availableSlotIds.add(
                slot.getSlotId()
        );

        System.out.println(
                "Slot Released & Returned "
                        + "to Priority Pool: "
                        + slot.getSlotId()
        );

        return true;
    }

    public boolean isSlotAvailable(
            String slotId
    ) {

        if (slotId == null
                || slotId.isBlank()) {

            return false;
        }

        return availableSlotIds.contains(
                slotId.trim().toUpperCase()
        );
    }
}