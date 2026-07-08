import java.util.LinkedList;
import java.util.Queue;

public class VehicleQueue {

    private Queue<String> queue;

    public VehicleQueue() {
        queue = new LinkedList<>();
    }

    // Add vehicle to queue
    public void enqueue(String licensePlate) {
        queue.offer(licensePlate);
        System.out.println("Vehicle " + licensePlate + " entered queue.");
    }

    // Remove vehicle from queue
    public String dequeue() {
        if (queue.isEmpty()) {
            System.out.println("Queue is empty.");
            return null;
        }
        String removed = queue.poll();
        System.out.println("Vehicle " + removed + " processed and exited.");
        return removed;
    }

    // Display queue
    public void displayQueue() {
        System.out.println("--- Current Vehicle Queue ---");
        for (String vehicle : queue) {
            System.out.println(vehicle);
        }
        System.out.println();
    }
}
