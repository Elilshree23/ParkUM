import java.util.LinkedList;
import java.util.Queue;

public class VehicleQueue {

    private final Queue<String> queue;

    public VehicleQueue() {
        queue = new LinkedList<>();
    }

    public boolean enqueue(String plate) {

        ValidationUtil.requirePlate(plate);

        if (queue.contains(plate.toUpperCase())) {
            System.out.println("Vehicle already waiting.");
            return false;
        }

        queue.offer(plate.toUpperCase());

        System.out.println("Queued : " + plate);

        return true;
    }

    public String dequeue() {

        if (queue.isEmpty()) {
            System.out.println("Queue empty.");
            return null;
        }

        String plate = queue.poll();

        System.out.println("Processing : " + plate);

        return plate;
    }

    public String peek() {
        return queue.peek();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size();
    }

    public void displayQueue() {

        System.out.println("\n------ Vehicle Queue ------");

        if (queue.isEmpty()) {
            System.out.println("Queue empty.");
        } else {

            int no = 1;

            for (String vehicle : queue) {
                System.out.println(no++ + ". " + vehicle);
            }
        }

        System.out.println("---------------------------");
    }
}