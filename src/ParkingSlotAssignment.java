import java.util.PriorityQueue;

class ParkingSlot implements Comparable<ParkingSlot> {

    private final String slotId;
    private final int distance;
    private boolean available;

    public ParkingSlot(String slotId, int distance) {

        ValidationUtil.requireText(slotId, "Parking Slot");

        if (distance < 0)
            throw new IllegalArgumentException("Distance cannot be negative.");

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
        return slotId + " (" + distance + "m)";
    }
}

public class ParkingSlotAssignment {

    private final PriorityQueue<ParkingSlot> availableSlots;

    public ParkingSlotAssignment() {
        availableSlots = new PriorityQueue<>();
    }

    public boolean addParkingSlot(ParkingSlot slot) {

        if (slot == null)
            return false;

        if (!slot.isAvailable())
            return false;

        if (contains(slot.getSlotId()))
            return false;

        availableSlots.offer(slot);
        return true;
    }

    public ParkingSlot assignSlot() {

        if (availableSlots.isEmpty()) {
            System.out.println("No parking slots available.");
            return null;
        }

        ParkingSlot slot = availableSlots.poll();
        slot.setAvailable(false);

        System.out.println("Assigned : " + slot);

        return slot;
    }

    public void releaseSlot(ParkingSlot slot) {

        if (slot == null)
            return;

        if (slot.isAvailable())
            return;

        slot.setAvailable(true);
        availableSlots.offer(slot);

        System.out.println("Released : " + slot.getSlotId());
    }

    public ParkingSlot peekNextSlot() {
        return availableSlots.peek();
    }

    public boolean isEmpty() {
        return availableSlots.isEmpty();
    }

    public int size() {
        return availableSlots.size();
    }

    public boolean contains(String slotId) {

        for (ParkingSlot slot : availableSlots) {
            if (slot.getSlotId().equalsIgnoreCase(slotId))
                return true;
        }

        return false;
    }

    public void displayAvailableSlots() {

        System.out.println("\n------ Available Parking Slots ------");

        if (availableSlots.isEmpty()) {
            System.out.println("No available slots.");
        } else {

            for (ParkingSlot slot : availableSlots) {
                System.out.println(slot);
            }
        }

        System.out.println("-------------------------------------");
    }
}