import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class ParkingGraph {

    static class Edge {

        private final String destination;
        private final int weight;

        Edge(
                String destination,
                int weight
        ) {
            this.destination = destination;
            this.weight = weight;
        }

        String getDestination() {
            return destination;
        }

        int getWeight() {
            return weight;
        }
    }

    private static class NodeDistance
            implements Comparable<NodeDistance> {

        private final String node;
        private final int distance;

        NodeDistance(
                String node,
                int distance
        ) {
            this.node = node;
            this.distance = distance;
        }

        @Override
        public int compareTo(
                NodeDistance other
        ) {
            return Integer.compare(
                    this.distance,
                    other.distance
            );
        }
    }

    private final Map<String, List<Edge>>
            adjacencyList;

    private final Set<String>
            availableParkingSlots;

    private Map<String, String>
            lastPrev;

    public ParkingGraph() {

        adjacencyList =
                new HashMap<>();

        availableParkingSlots =
                new HashSet<>();

        lastPrev =
                new HashMap<>();
    }

    public boolean addNode(
            String node
    ) {

        if (node == null
                || node.isBlank()) {

            System.out.println(
                    "Error: Node name "
                            + "cannot be null or empty."
            );

            return false;
        }

        String normalizedNode =
                node.trim().toUpperCase();

        if (adjacencyList.containsKey(
                normalizedNode
        )) {

            System.out.println(
                    "Error: Node "
                            + normalizedNode
                            + " already exists."
            );

            return false;
        }

        adjacencyList.put(
                normalizedNode,
                new ArrayList<>()
        );

        return true;
    }

    public boolean addEdge(
            String source,
            String destination,
            int weight
    ) {

        if (source == null
                || source.isBlank()
                || destination == null
                || destination.isBlank()) {

            System.out.println(
                    "Error: Node names "
                            + "cannot be null or empty."
            );

            return false;
        }

        if (weight <= 0) {

            System.out.println(
                    "Error: Edge weight "
                            + "must be greater than 0."
            );

            return false;
        }

        String normalizedSource =
                source.trim().toUpperCase();

        String normalizedDestination =
                destination.trim().toUpperCase();

        adjacencyList.putIfAbsent(
                normalizedSource,
                new ArrayList<>()
        );

        adjacencyList.putIfAbsent(
                normalizedDestination,
                new ArrayList<>()
        );

        boolean edgeExists =
                adjacencyList
                        .get(normalizedSource)
                        .stream()
                        .anyMatch(
                                edge ->
                                        edge.getDestination()
                                                .equals(
                                                        normalizedDestination
                                                )
                        );

        if (edgeExists) {

            System.out.println(
                    "Error: Edge between "
                            + normalizedSource
                            + " and "
                            + normalizedDestination
                            + " already exists."
            );

            return false;
        }

        adjacencyList
                .get(normalizedSource)
                .add(
                        new Edge(
                                normalizedDestination,
                                weight
                        )
                );

        adjacencyList
                .get(normalizedDestination)
                .add(
                        new Edge(
                                normalizedSource,
                                weight
                        )
                );

        return true;
    }

    public boolean markSlotAvailable(
            String parkingNode
    ) {

        if (parkingNode == null
                || parkingNode.isBlank()) {

            System.out.println(
                    "Error: Parking node "
                            + "cannot be null or empty."
            );

            return false;
        }

        String normalizedNode =
                parkingNode.trim().toUpperCase();

        if (!adjacencyList.containsKey(
                normalizedNode
        )) {

            System.out.println(
                    "Error: Parking node "
                            + normalizedNode
                            + " does not exist "
                            + "in the graph."
            );

            return false;
        }

        if (availableParkingSlots.contains(
                normalizedNode
        )) {

            System.out.println(
                    "[Slot] Warning: "
                            + normalizedNode
                            + " is already AVAILABLE."
            );

            return false;
        }

        availableParkingSlots.add(
                normalizedNode
        );

        System.out.println(
                "[Slot] "
                        + normalizedNode
                        + " is now AVAILABLE."
        );

        return true;
    }

    public boolean markSlotOccupied(
            String parkingNode
    ) {

        if (parkingNode == null
                || parkingNode.isBlank()) {

            System.out.println(
                    "Error: Parking node "
                            + "cannot be null or empty."
            );

            return false;
        }

        String normalizedNode =
                parkingNode.trim().toUpperCase();

        if (!availableParkingSlots.contains(
                normalizedNode
        )) {

            System.out.println(
                    "[Slot] Error: "
                            + normalizedNode
                            + " is not currently AVAILABLE."
            );

            return false;
        }

        availableParkingSlots.remove(
                normalizedNode
        );

        System.out.println(
                "[Slot] "
                        + normalizedNode
                        + " is now OCCUPIED."
        );

        return true;
    }

    public boolean isAvailable(
            String parkingNode
    ) {

        if (parkingNode == null
                || parkingNode.isBlank()) {

            return false;
        }

        return availableParkingSlots.contains(
                parkingNode.trim().toUpperCase()
        );
    }

    public Map<String, Integer> dijkstra(
            String source
    ) {

        Map<String, Integer> distances =
                new HashMap<>();

        Map<String, String> previous =
                new HashMap<>();

        for (String node :
                adjacencyList.keySet()) {

            distances.put(
                    node,
                    Integer.MAX_VALUE
            );

            previous.put(
                    node,
                    null
            );
        }

        if (source == null
                || source.isBlank()) {

            System.out.println(
                    "Error: Source node "
                            + "cannot be empty."
            );

            lastPrev = previous;

            return distances;
        }

        String normalizedSource =
                source.trim().toUpperCase();

        if (!adjacencyList.containsKey(
                normalizedSource
        )) {

            System.out.println(
                    "Error: Source node '"
                            + normalizedSource
                            + "' does not exist."
            );

            lastPrev = previous;

            return distances;
        }

        PriorityQueue<NodeDistance>
                priorityQueue =
                new PriorityQueue<>();

        Set<String> visited =
                new HashSet<>();

        distances.put(
                normalizedSource,
                0
        );

        priorityQueue.offer(
                new NodeDistance(
                        normalizedSource,
                        0
                )
        );

        while (!priorityQueue.isEmpty()) {

            NodeDistance current =
                    priorityQueue.poll();

            if (visited.contains(
                    current.node
            )) {

                continue;
            }

            visited.add(
                    current.node
            );

            List<Edge> neighbours =
                    adjacencyList.getOrDefault(
                            current.node,
                            Collections.emptyList()
                    );

            for (Edge edge :
                    neighbours) {

                if (visited.contains(
                        edge.getDestination()
                )) {

                    continue;
                }

                int newDistance =
                        current.distance
                                + edge.getWeight();

                if (newDistance
                        < distances.get(
                        edge.getDestination()
                )) {

                    distances.put(
                            edge.getDestination(),
                            newDistance
                    );

                    previous.put(
                            edge.getDestination(),
                            current.node
                    );

                    priorityQueue.offer(
                            new NodeDistance(
                                    edge.getDestination(),
                                    newDistance
                            )
                    );
                }
            }
        }

        lastPrev = previous;

        return distances;
    }

    public List<String> getPath(
            String source,
            String destination
    ) {

        if (source == null
                || destination == null) {

            return Collections.emptyList();
        }

        String normalizedSource =
                source.trim().toUpperCase();

        String normalizedDestination =
                destination.trim().toUpperCase();

        LinkedList<String> path =
                new LinkedList<>();

        String current =
                normalizedDestination;

        while (current != null) {

            path.addFirst(
                    current
            );

            current =
                    lastPrev.get(
                            current
                    );
        }

        if (path.isEmpty()
                || !path.getFirst()
                .equals(
                        normalizedSource
                )) {

            return Collections.emptyList();
        }

        return path;
    }

    public void navigateToNearestSlot(
            String driverLocation
    ) {

        if (driverLocation == null
                || driverLocation.isBlank()) {

            System.out.println(
                    "[Navigator] Invalid "
                            + "driver location."
            );

            return;
        }

        String normalizedLocation =
                driverLocation.trim()
                        .toUpperCase();

        System.out.println(
                "\n[Navigator] Searching for "
                        + "nearest available slot from: "
                        + normalizedLocation
        );

        if (availableParkingSlots.isEmpty()) {

            System.out.println(
                    "[Navigator] No available "
                            + "parking slots at this time."
            );

            return;
        }

        Map<String, Integer> distances =
                dijkstra(
                        normalizedLocation
                );

        String bestSlot = null;

        int bestDistance =
                Integer.MAX_VALUE;

        for (String slot :
                availableParkingSlots) {

            int distance =
                    distances.getOrDefault(
                            slot,
                            Integer.MAX_VALUE
                    );

            if (distance
                    < bestDistance) {

                bestDistance =
                        distance;

                bestSlot =
                        slot;
            }
        }

        if (bestSlot == null
                || bestDistance
                == Integer.MAX_VALUE) {

            System.out.println(
                    "[Navigator] No reachable "
                            + "parking slot found."
            );

            return;
        }

        List<String> path =
                getPath(
                        normalizedLocation,
                        bestSlot
                );

        System.out.println(
                "[Navigator] Nearest available slot: "
                        + bestSlot
        );

        System.out.println(
                "[Navigator] Total distance/cost: "
                        + bestDistance
                        + " units"
        );

        System.out.println(
                "[Navigator] Recommended route: "
                        + String.join(
                        " --> ",
                        path
                )
        );
    }

    public void displayGraph() {

        System.out.println(
                "\n Parking Location Graph "
                        + "(Adjacency List) "
        );

        for (
                Map.Entry<
                        String,
                        List<Edge>
                        > entry
                : adjacencyList.entrySet()
        ) {

            System.out.print(
                    "  "
                            + entry.getKey()
                            + " : "
            );

            List<String> neighbours =
                    new ArrayList<>();

            for (Edge edge :
                    entry.getValue()) {

                neighbours.add(
                        edge.getDestination()
                                + "("
                                + edge.getWeight()
                                + ")"
                );
            }

            System.out.println(
                    String.join(
                            ", ",
                            neighbours
                    )
            );
        }

        System.out.println(
                "--------------------------------------------------\n"
        );
    }

    public void displayAllDistances(
            String source
    ) {

        Map<String, Integer> distances =
                dijkstra(source);

        System.out.println(
                "\n Shortest Distances from ["
                        + source
                        + "] "
        );

        distances.entrySet()
                .stream()
                .sorted(
                        Map.Entry.comparingByValue()
                )
                .forEach(
                        entry ->
                                System.out.printf(
                                        "  %-20s --> %d units%n",
                                        entry.getKey(),
                                        entry.getValue()
                                                == Integer.MAX_VALUE
                                                ? -1
                                                : entry.getValue()
                                )
                );

        System.out.println(
                "----------------------------------------------------\n"
        );
    }
}