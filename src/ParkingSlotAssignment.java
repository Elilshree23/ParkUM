import java.util.PriorityQueue;

// Class representing a Parking Slot
class ParkingSlot implements Comparable<ParkingSlot> {
    private String slotId;
    private int distance; // Priority factor: lower distance = higher priority
    private boolean isAvailable;

    public ParkingSlot(String slotId, int distance) {
        this.slotId = slotId;
        this.distance = distance;
        this.isAvailable = true;
    }

    public String getSlotId() {
        return slotId; }
    public int getDistance() {
        return distance; }
    public boolean isAvailable() {
        return isAvailable; }
    public void setAvailable(boolean available) {
        this.isAvailable = available; }

    // Defines priority: Slots with shorter distances are assigned first (Min-Heap behavior)
    @Override
    public int compareTo(ParkingSlot other) {
        return Integer.compare(this.distance, other.distance);
    }

    @Override
    public String toString() {
        return "Slot " + slotId + " (Distance: " + distance + "m)";
    }
}

// Module managing the Priority Queue assignment
public class ParkingSlotAssignment {
    // Min-Heap to keep track of available slots with the highest priority at the root
    private PriorityQueue<ParkingSlot> availableSlots;

    public ParkingSlotAssignment() {
        this.availableSlots = new PriorityQueue<>();
    }

    // Add a new slot to the system (O(log n))
    public void addParkingSlot(ParkingSlot slot) {
        if (slot.isAvailable()) {
            availableSlots.add(slot);
        }
    }

    // Assign the highest priority slot (O(log n))
    public ParkingSlot assignSlot() {
        if (availableSlots.isEmpty()) {
            System.out.println("Assignment Failed: No available parking slots!");
            return null;
        }

        // Poll removes and returns the highest priority (nearest) slot from the heap
        ParkingSlot assignedSlot = availableSlots.poll();
        assignedSlot.setAvailable(false);
        System.out.println("Successfully Assigned: " + assignedSlot);
        return assignedSlot;
    }

    // Release a slot back to the system when a vehicle leaves (O(log n))
    public void releaseSlot(ParkingSlot slot) {
        slot.setAvailable(true);
        availableSlots.add(slot);
        System.out.println("Slot Released & Returned to Priority Pool: " + slot.getSlotId());
    }
}

