import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections;
import java.util.LinkedList;

public class ParkingGraph {

    static class Edge {
        private final String destination;
        private final int weight; // distance / travel-time in metres or seconds

        Edge(String destination, int weight) {
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

    private final Map<String, List<Edge>> adjacencyList = new HashMap<>();
    private final Set<String> availableParkingSlots = new HashSet<>();
    private Map<String, String> lastPrev = new HashMap<>();

    public void addNode(String node) {
        adjacencyList.putIfAbsent(node, new ArrayList<>());
    }

    public void addEdge(String source, String destination, int weight) {
        adjacencyList.putIfAbsent(source, new ArrayList<>());
        adjacencyList.putIfAbsent(destination, new ArrayList<>());

        adjacencyList.get(source).add(new Edge(destination, weight));
        adjacencyList.get(destination).add(new Edge(source, weight));
    }

    public void markSlotAvailable(String parkingNode) {
        availableParkingSlots.add(parkingNode);
        System.out.println("[Slot] " + parkingNode + " is now AVAILABLE.");
    }

    public void markSlotOccupied(String parkingNode) {
        availableParkingSlots.remove(parkingNode);
        System.out.println("[Slot] " + parkingNode + " is now OCCUPIED.");
    }

    public boolean isAvailable(String parkingNode) {
        return availableParkingSlots.contains(parkingNode);
    }

    public Map<String, Integer> dijkstra(String source) {
        Map<String, Integer> dist = new HashMap<>();
        Map<String, String> prev = new HashMap<>();
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));

        Map<String, Integer> nodeIndex = new HashMap<>();
        List<String> nodes = new ArrayList<>(adjacencyList.keySet());
        for (int i = 0; i < nodes.size(); i++) nodeIndex.put(nodes.get(i), i);

        for (String node : adjacencyList.keySet()) {
            dist.put(node, Integer.MAX_VALUE);
            prev.put(node, null);
        }

        dist.put(source, 0);
        pq.offer(new int[]{0, nodeIndex.getOrDefault(source, -1)});

        Map<Integer, String> indexToNode = new HashMap<>();
        for (Map.Entry<String, Integer> e : nodeIndex.entrySet())
            indexToNode.put(e.getValue(), e.getKey());

        Set<String> visited = new HashSet<>();

        while (!pq.isEmpty()) {
            int[] entry = pq.poll();
            int currDist = entry[0];
            String currNode = indexToNode.get(entry[1]);

            if (currNode == null || visited.contains(currNode)) continue;
            visited.add(currNode);

            List<Edge> neighbours = adjacencyList.getOrDefault(currNode, Collections.emptyList());
            for (Edge edge : neighbours) {
                if (visited.contains(edge.getDestination())) continue;

                int newDist = currDist + edge.getWeight();
                if (newDist < dist.getOrDefault(edge.getDestination(), Integer.MAX_VALUE)) {
                    dist.put(edge.getDestination(), newDist);
                    prev.put(edge.getDestination(), currNode);
                    int idx = nodeIndex.getOrDefault(edge.getDestination(), -1);
                    if (idx != -1) pq.offer(new int[]{newDist, idx});
                }
            }
        }

        this.lastPrev = prev;
        return dist;
    }

    public List<String> getPath(String source, String destination) {
        LinkedList<String> path = new LinkedList<>();
        String current = destination;

        while (current != null) {
            path.addFirst(current);
            current = lastPrev.get(current);
        }

        if (!path.isEmpty() && !path.getFirst().equals(source)) return Collections.emptyList();
        return path;
    }

    public void navigateToNearestSlot(String driverLocation) {
        System.out.println("\n[Navigator] Searching for nearest available slot from: " + driverLocation);

        if (availableParkingSlots.isEmpty()) {
            System.out.println("[Navigator] No available parking slots at this time.");
            return;
        }

        Map<String, Integer> distances = dijkstra(driverLocation);

        String bestSlot = null;
        int bestDistance = Integer.MAX_VALUE;

        for (String slot : availableParkingSlots) {
            int d = distances.getOrDefault(slot, Integer.MAX_VALUE);
            if (d < bestDistance) {
                bestDistance = d;
                bestSlot = slot;
            }
        }

        if (bestSlot == null || bestDistance == Integer.MAX_VALUE) {
            System.out.println("[Navigator] No reachable parking slot found.");
            return;
        }

        List<String> path = getPath(driverLocation, bestSlot);

        System.out.println("[Navigator] Nearest available slot  : " + bestSlot);
        System.out.println("[Navigator] Total distance/cost     : " + bestDistance + " units");
        System.out.print("[Navigator] Recommended route       : ");
        System.out.println(String.join(" --> ", path));
    }

    public void displayGraph() {
        System.out.println("\n Parking Location Graph (Adjacency List) ");
        for (Map.Entry<String, List<Edge>> entry : adjacencyList.entrySet()) {
            System.out.print("  " + entry.getKey() + " : ");
            List<String> neighbours = new ArrayList<>();
            for (Edge e : entry.getValue())
                neighbours.add(e.getDestination() + "(" + e.getWeight() + ")");
            System.out.println(String.join(", ", neighbours));
        }
        System.out.println("--------------------------------------------------\n");
    }

    public void displayAllDistances(String source) {
        Map<String, Integer> dist = dijkstra(source);
        System.out.println("\n Shortest Distances from [" + source + "] ");
        dist.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(e -> System.out.printf("  %-20s --> %d units%n", e.getKey(), e.getValue() == Integer.MAX_VALUE ? -1 : e.getValue()));
        System.out.println("----------------------------------------------------\n");
    }

    public static void main(String[] args) {
        ParkingGraph graph = new ParkingGraph();

        graph.addEdge("Entry_A", "Junction_1", 10);
        graph.addEdge("Entry_A", "Junction_2", 20);
        graph.addEdge("Junction_1", "ParkingLot_B", 15);
        graph.addEdge("Junction_1", "Junction_3", 12);
        graph.addEdge("Junction_2", "Junction_3", 18);
        graph.addEdge("Junction_2", "ParkingLot_A", 25);
        graph.addEdge("Junction_3", "ParkingLot_C", 10);
        graph.addEdge("ParkingLot_B", "ParkingLot_C", 8);

        graph.displayGraph();

        graph.markSlotAvailable("ParkingLot_A");
        graph.markSlotAvailable("ParkingLot_B");
        graph.markSlotAvailable("ParkingLot_C");

        graph.displayAllDistances("Entry_A");
        graph.navigateToNearestSlot("Entry_A");

        System.out.println("\n ParkingLot_B is now occupied ");
        graph.markSlotOccupied("ParkingLot_B");
        graph.navigateToNearestSlot("Entry_A");

        System.out.println("\n New driver arrives at Junction_2 ");
        graph.navigateToNearestSlot("Junction_2");
    }
}