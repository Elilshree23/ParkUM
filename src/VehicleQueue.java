import java.util.LinkedList;
import java.util.Queue;

public class VehicleQueue {

    private final Queue<String> vehicleQueue;

    public VehicleQueue() {
        vehicleQueue = new LinkedList<>();
    }

    public void enqueue(String licensePlate) {
        vehicleQueue.offer(licensePlate);
        System.out.println("Vehicle " + licensePlate + " entered queue.");
    }

    public String dequeue() {
        if (vehicleQueue.isEmpty()) {
            System.out.println("Queue is empty.");
            return null;
        }
        String removed = vehicleQueue.poll();
        System.out.println("Vehicle " + removed + " processed and exited.");
        return removed;
    }

    public void displayQueue() {
        System.out.println("--- Current Vehicle Queue ---");
        for (String vehicle : vehicleQueue) {
            System.out.println(vehicle);
        }
        System.out.println();
    }
}