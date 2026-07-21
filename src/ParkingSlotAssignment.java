import java.util.PriorityQueue;

class ParkingSlot implements Comparable<ParkingSlot> {
    private final String slotId;
    private final int distance; // Priority factor: lower distance = higher priority
    private boolean available;

    public ParkingSlot(String slotId, int distance) {
        this.slotId = slotId;
        this.distance = distance;
        this.available = true;
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

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public int compareTo(ParkingSlot other) {
        return Integer.compare(this.distance, other.distance);
    }

    @Override
    public String toString() {
        return "Slot " + slotId + " (Distance: " + distance + "m)";
    }
}

public class ParkingSlotAssignment {
    private final PriorityQueue<ParkingSlot> availableSlots;

    public ParkingSlotAssignment() {
        this.availableSlots = new PriorityQueue<>();
    }

    public void addParkingSlot(ParkingSlot slot) {
        if (slot != null && slot.isAvailable()) {
            availableSlots.add(slot);
        }
    }

    public ParkingSlot assignSlot() {
        if (availableSlots.isEmpty()) {
            System.out.println("Assignment Failed: No available parking slots!");
            return null;
        }

        ParkingSlot assignedSlot = availableSlots.poll();
        assignedSlot.setAvailable(false);
        System.out.println("Successfully Assigned: " + assignedSlot);
        return assignedSlot;
    }

    public void releaseSlot(ParkingSlot slot) {
        if (slot == null) {
            return;
        }
        slot.setAvailable(true);
        availableSlots.add(slot);
        System.out.println("Slot Released & Returned to Priority Pool: " + slot.getSlotId());
    }
}