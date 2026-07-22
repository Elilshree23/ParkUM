import java.util.*;

public class ParkingGraph {

    /*====================================================
                      EDGE CLASS
    ====================================================*/
    private static class Edge {

        private final String destination;
        private final int weight;

        public Edge(String destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }

        public String getDestination() {
            return destination;
        }

        public int getWeight() {
            return weight;
        }
    }

    /*====================================================
                    DATA STRUCTURES
    ====================================================*/

    private final Map<String, List<Edge>> adjacencyList;
    private final Set<String> availableParkingSlots;
    private Map<String, String> previousNode;

    public ParkingGraph() {

        adjacencyList = new LinkedHashMap<>();
        availableParkingSlots = new LinkedHashSet<>();
        previousNode = new HashMap<>();

    }

    /*====================================================
                    GRAPH CREATION
    ====================================================*/

    public void addNode(String node) {

        ValidationUtil.requireText(node, "Node");

        adjacencyList.putIfAbsent(node, new ArrayList<>());
    }

    public boolean containsNode(String node) {
        return adjacencyList.containsKey(node);
    }

    public void addEdge(String source, String destination, int weight) {

        ValidationUtil.requireText(source, "Source");
        ValidationUtil.requireText(destination, "Destination");

        if (weight <= 0)
            throw new IllegalArgumentException("Weight must be greater than zero.");

        addNode(source);
        addNode(destination);

        if (!edgeExists(source, destination)) {
            adjacencyList.get(source).add(new Edge(destination, weight));
            adjacencyList.get(destination).add(new Edge(source, weight));
        }
    }

    private boolean edgeExists(String source, String destination) {

        List<Edge> edges = adjacencyList.get(source);

        if (edges == null)
            return false;

        for (Edge edge : edges) {

            if (edge.getDestination().equalsIgnoreCase(destination))
                return true;
        }

        return false;
    }

    /*====================================================
                PARKING SLOT MANAGEMENT
    ====================================================*/

    public void markSlotAvailable(String slot) {

        if (!containsNode(slot))
            addNode(slot);

        availableParkingSlots.add(slot);

        System.out.println("[AVAILABLE] " + slot);
    }

    public void markSlotOccupied(String slot) {

        availableParkingSlots.remove(slot);

        System.out.println("[OCCUPIED] " + slot);
    }

    public boolean isAvailable(String slot) {
        return availableParkingSlots.contains(slot);
    }

    public int getAvailableSlotCount() {
        return availableParkingSlots.size();
    }

    public Set<String> getAvailableSlots() {
        return new HashSet<>(availableParkingSlots);
    }

    /*====================================================
                    DIJKSTRA
    ====================================================*/

    public Map<String, Integer> dijkstra(String source) {

        ValidationUtil.requireText(source, "Source");

        if (!containsNode(source))
            throw new IllegalArgumentException("Source node not found.");

        previousNode.clear();

        Map<String, Integer> distance = new HashMap<>();

        for (String node : adjacencyList.keySet()) {
            distance.put(node, Integer.MAX_VALUE);
        }

        distance.put(source, 0);

        PriorityQueue<NodeDistance> pq = new PriorityQueue<>();

        pq.offer(new NodeDistance(source, 0));

        while (!pq.isEmpty()) {

            NodeDistance current = pq.poll();

            if (current.distance > distance.get(current.node))
                continue;

            for (Edge edge : adjacencyList.get(current.node)) {

                int newDistance = current.distance + edge.getWeight();

                if (newDistance < distance.get(edge.getDestination())) {

                    distance.put(edge.getDestination(), newDistance);

                    previousNode.put(edge.getDestination(), current.node);

                    pq.offer(new NodeDistance(
                            edge.getDestination(),
                            newDistance));
                }
            }
        }

        return distance;
    }

    /*====================================================
            PRIORITY QUEUE HELPER CLASS
    ====================================================*/

    private static class NodeDistance
            implements Comparable<NodeDistance> {

        private final String node;
        private final int distance;

        public NodeDistance(String node, int distance) {
            this.node = node;
            this.distance = distance;
        }

        @Override
        public int compareTo(NodeDistance other) {
            return Integer.compare(distance, other.distance);
        }
    }

        /*====================================================
                PATH RECONSTRUCTION
    ====================================================*/

    public List<String> getPath(String source, String destination) {

        LinkedList<String> path = new LinkedList<>();

        if (!containsNode(source) || !containsNode(destination))
            return path;

        String current = destination;

        while (current != null) {
            path.addFirst(current);
            current = previousNode.get(current);
        }

        if (path.isEmpty() || !path.getFirst().equals(source))
            return new ArrayList<>();

        return path;
    }

    /*====================================================
                  NAVIGATION SYSTEM
    ====================================================*/

    public void navigateToNearestSlot(String source) {

        ValidationUtil.requireText(source, "Source");

        if (availableParkingSlots.isEmpty()) {
            System.out.println("\nNo parking slots available.");
            return;
        }

        Map<String, Integer> distance = dijkstra(source);

        String bestSlot = null;
        int shortestDistance = Integer.MAX_VALUE;

        for (String slot : availableParkingSlots) {

            int currentDistance =
                    distance.getOrDefault(slot, Integer.MAX_VALUE);

            if (currentDistance < shortestDistance) {
                shortestDistance = currentDistance;
                bestSlot = slot;
            }
        }

        if (bestSlot == null) {
            System.out.println("\nNo reachable parking slot found.");
            return;
        }

        List<String> route = getPath(source, bestSlot);

        System.out.println("\n========== SMART NAVIGATION ==========");
        System.out.println("Start Location : " + source);
        System.out.println("Assigned Slot  : " + bestSlot);
        System.out.println("Distance       : " + shortestDistance + " m");
        System.out.println("Route          : " + String.join(" -> ", route));
        System.out.println("======================================");
    }

    /*====================================================
                    DISPLAY GRAPH
    ====================================================*/
    public void navigateToSlot(String source, String destination) {


        Map<String,Integer> distance = dijkstra(source);


        List<String> route = getPath(
                source,
                destination
        );


        if(route.isEmpty()) {

            System.out.println("No route available.");

            return;
        }



        System.out.println("\n========== SMART NAVIGATION ==========");

        System.out.println(
                "Start Location : "
                        + source
        );


        System.out.println(
                "Destination   : "
                        + destination
        );


        System.out.println(
                "Distance      : "
                        + distance.get(destination)
                        + " m"
        );


        System.out.println(
                "Route         : "
                        + String.join(" -> ",route)
        );


        System.out.println(
                "======================================"
        );

    }

    public void displayGraph() {

        System.out.println("\n========== CAMPUS MAP ==========");

        for (String node : adjacencyList.keySet()) {

            System.out.print(node + " -> ");

            List<String> neighbours = new ArrayList<>();

            for (Edge edge : adjacencyList.get(node)) {

                neighbours.add(
                        edge.getDestination()
                                + "("
                                + edge.getWeight()
                                + "m)");
            }

            System.out.println(String.join(", ", neighbours));
        }

        System.out.println("================================\n");
    }

    /*====================================================
                DISPLAY DISTANCES
    ====================================================*/

    public void displayAllDistances(String source) {

        Map<String, Integer> distance = dijkstra(source);

        System.out.println("\nShortest Distance from " + source);

        distance.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(entry -> {

                    int value = entry.getValue();

                    if (value == Integer.MAX_VALUE)
                        System.out.println(
                                entry.getKey()
                                        + " : Unreachable");

                    else
                        System.out.println(
                                entry.getKey()
                                        + " : "
                                        + value
                                        + " m");
                });

        System.out.println();
    }

    /*====================================================
                    UTILITY METHODS
    ====================================================*/

    public int getNumberOfNodes() {
        return adjacencyList.size();
    }

    public boolean hasAvailableSlots() {
        return !availableParkingSlots.isEmpty();
    }

    public void displayAvailableSlots() {

        System.out.println("\nAvailable Slots");

        if (availableParkingSlots.isEmpty()) {
            System.out.println("None");
            return;
        }

        for (String slot : availableParkingSlots) {
            System.out.println("- " + slot);
        }
    }

    /*====================================================
                        DEMO
    ====================================================*/

    public static void main(String[] args) {

        ParkingGraph graph = new ParkingGraph();

        graph.addEdge("Main_Gate",
                "Central_Roundabout", 10);

        graph.addEdge("Central_Roundabout",
                "North_Wing", 15);

        graph.addEdge("Central_Roundabout",
                "East_Wing", 25);

        graph.addEdge("North_Wing",
                "Slot_N1", 5);

        graph.addEdge("North_Wing",
                "Slot_N2", 10);

        graph.addEdge("East_Wing",
                "Slot_E1", 5);

        graph.markSlotAvailable("Slot_N1");
        graph.markSlotAvailable("Slot_N2");
        graph.markSlotAvailable("Slot_E1");

        graph.displayGraph();

        graph.displayAvailableSlots();

        graph.displayAllDistances("Main_Gate");

        graph.navigateToNearestSlot("Main_Gate");

        graph.markSlotOccupied("Slot_N1");

        graph.navigateToNearestSlot("Main_Gate");
    }

    public void displayParkingStatus() {

        System.out.println("\n========== PARKING STATUS ==========");

        int total = 0;
        int available = availableParkingSlots.size();

        for(String node : adjacencyList.keySet()) {

            if(node.startsWith("Slot")) {

                total++;

                String status;

                if(availableParkingSlots.contains(node)) {
                    status = "AVAILABLE";
                }
                else {
                    status = "OCCUPIED";
                }

                System.out.println(
                        node + " : " + status
                );
            }
        }

        System.out.println("-------------------------------");
        System.out.println("Total Slots     : " + total);
        System.out.println("Available Slots : " + available);
        System.out.println("Occupied Slots  : " + (total - available));
        System.out.println("===================================");
    }
}