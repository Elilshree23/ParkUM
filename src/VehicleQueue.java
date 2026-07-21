import java.util.LinkedList;
import java.util.Queue;

public class VehicleQueue {

    private final Queue<String>
            vehicleQueue;

    public VehicleQueue() {

        vehicleQueue =
                new LinkedList<>();
    }

    public boolean enqueue(
            String licensePlate
    ) {

        if (licensePlate == null
                || licensePlate.isBlank()) {

            System.out.println(
                    "Error: License plate "
                            + "cannot be empty."
            );

            return false;
        }

        String normalizedPlate =
                licensePlate.trim().toUpperCase();

        if (vehicleQueue.contains(
                normalizedPlate
        )) {

            System.out.println(
                    "Error: Vehicle "
                            + normalizedPlate
                            + " is already in the queue."
            );

            return false;
        }

        vehicleQueue.offer(
                normalizedPlate
        );

        System.out.println(
                "Vehicle "
                        + normalizedPlate
                        + " entered queue."
        );

        return true;
    }

    public String dequeue() {

        if (vehicleQueue.isEmpty()) {

            System.out.println(
                    "Queue is empty."
            );

            return null;
        }

        String removed =
                vehicleQueue.poll();

        System.out.println(
                "Vehicle "
                        + removed
                        + " processed and exited."
        );

        return removed;
    }

    public void displayQueue() {

        if (vehicleQueue.isEmpty()) {

            System.out.println(
                    "Queue is currently empty."
            );

            return;
        }

        System.out.println(
                "--- Current Vehicle Queue ---"
        );

        for (
                String vehicle
                : vehicleQueue
        ) {

            System.out.println(
                    vehicle
            );
        }

        System.out.println();
    }
}