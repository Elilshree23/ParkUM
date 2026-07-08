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
        String destination;
        int weight;          // distance / travel-time in metres or seconds

        Edge(String destination, int weight) {
            this.destination = destination;
            this.weight= weight;
        }
    }

    // Graph state
    
    // Adjacency list: node name --> list of edges leading out of it
    private final Map<String, List<Edge>> adjacencyList = new HashMap<>();

    // Tracks which parking nodes are currently available
    private final Set<String> availableParkingSlots = new HashSet<>();

    // Graph construction

    // Add a location node (intersection, entrance, parking area, etc.)
    public void addNode(String node) {
        adjacencyList.putIfAbsent(node, new ArrayList<>());
    }
    public void addEdge(String source, String destination, int weight) {
        adjacencyList.putIfAbsent(source,new ArrayList<>());
        adjacencyList.putIfAbsent(destination,new ArrayList<>());

        adjacencyList.get(source).add(new Edge(destination, weight));
        adjacencyList.get(destination).add(new Edge(source,      weight));
    }
    

    // Parking slot availability

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
    

    // Dijkstra's Algorithm

    public Map<String, Integer> dijkstra(String source) {
        // dist[node] = shortest known distance from source
        Map<String, Integer> dist = new HashMap<>();

        // previous[node] = the node we came from on the shortest path
        Map<String, String> prev = new HashMap<>();

        // Min-heap: (distance, node) — always processes the closest node first
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));

        // Map to convert index back to node name 
        Map<String, Integer> nodeIndex = new HashMap<>();
        List<String>nodes= new ArrayList<>(adjacencyList.keySet());
        for (int i = 0; i < nodes.size(); i++) nodeIndex.put(nodes.get(i), i);

        // Initialise all distances to infinity
        for (String node : adjacencyList.keySet()) {
            dist.put(node, Integer.MAX_VALUE);
            prev.put(node, null);
        }

        dist.put(source, 0);
        // Priority queue entry: [distance, nodeIndex]
        pq.offer(new int[]{0, nodeIndex.getOrDefault(source, -1)});

        // Store index-to-name mapping for PQ lookups
        Map<Integer, String> indexToNode = new HashMap<>();
        for (Map.Entry<String, Integer> e : nodeIndex.entrySet())
            indexToNode.put(e.getValue(), e.getKey());

        Set<String> visited = new HashSet<>();

        while (!pq.isEmpty()) {
            int[]entry= pq.poll();
            int currDist = entry[0];
            String currNode = indexToNode.get(entry[1]);

            if (currNode == null || visited.contains(currNode)) continue;
            visited.add(currNode);

            List<Edge> neighbours = adjacencyList.getOrDefault(currNode, Collections.emptyList());
            for (Edge edge : neighbours) {
                if (visited.contains(edge.destination)) continue;

                int newDist = currDist + edge.weight;
                if (newDist < dist.getOrDefault(edge.destination, Integer.MAX_VALUE)) {
                    dist.put(edge.destination, newDist);
                    prev.put(edge.destination, currNode);
                    int idx = nodeIndex.getOrDefault(edge.destination, -1);
                    if (idx != -1) pq.offer(new int[]{newDist, idx});
                }
            }
        }

        // Store 'prev' for path reconstruction — package with dist using a side-effect field
        this.lastPrev = prev;
        return dist;
    }

    // Stores predecessor map from the last Dijkstra call 
    private Map<String, String> lastPrev = new HashMap<>();

    /**
     * Reconstructs the full path from source to destination
     * using the predecessor map stored by the last Dijkstra() call.
     */
    public List<String> getPath(String source, String destination) {
        LinkedList<String> path = new LinkedList<>();
        String current = destination;

        while (current != null) {
            path.addFirst(current);
            current = lastPrev.get(current);
        }

        // If the first element is not the source, no path exists
        if (!path.isEmpty() && !path.getFirst().equals(source)) return Collections.emptyList();
        return path;
    }
    

    // High-level navigation: find the shortest path to nearest available slot

    public void navigateToNearestSlot(String driverLocation) {
        System.out.println("\n[Navigator] Searching for nearest available slot from: " + driverLocation);

        if (availableParkingSlots.isEmpty()) {
            System.out.println("[Navigator] No available parking slots at this time.");
            return;
        }

        // Single Dijkstra run covers all destinations simultaneously — O((V+E) log V)
        Map<String, Integer> distances = dijkstra(driverLocation);

        String bestSlot= null;
        int bestDistance = Integer.MAX_VALUE;

        for (String slot : availableParkingSlots) {
            int d = distances.getOrDefault(slot, Integer.MAX_VALUE);
            if (d < bestDistance) {
                bestDistance = d;
                bestSlot= slot;
            }
        }

        if (bestSlot == null || bestDistance == Integer.MAX_VALUE) {
            System.out.println("[Navigator] No reachable parking slot found.");
            return;
        }

        List<String> path = getPath(driverLocation, bestSlot);

        System.out.println("[Navigator] Nearest available slot  : " + bestSlot);
        System.out.println("[Navigator] Total distance/cost     : " + bestDistance + " units");
        System.out.print  ("[Navigator] Recommended route       : ");
        System.out.println(String.join(" --> ", path));
    }

    
    // Display helpers

    //Prints the full adjacency list of the graph
    public void displayGraph() {
        System.out.println("\n Parking Location Graph (Adjacency List) ");
        for (Map.Entry<String, List<Edge>> entry : adjacencyList.entrySet()) {
            System.out.print("  " + entry.getKey() + " : ");
            List<String> neighbours = new ArrayList<>();
            for (Edge e : entry.getValue())
                neighbours.add(e.destination + "(" + e.weight + ")");
            System.out.println(String.join(", ", neighbours));
        }
        System.out.println("--------------------------------------------------\n");
    }

    //Print out all the shortest distances from a given source 
    public void displayAllDistances(String source) {
        Map<String, Integer> dist = dijkstra(source);
        System.out.println("\n Shortest Distances from [" + source + "] ");
        dist.entrySet().stream()
            .sorted(Map.Entry.comparingByValue())
            .forEach(e -> System.out.printf("  %-20s --> %d units%n", e.getKey(), e.getValue() == Integer.MAX_VALUE ? -1 : e.getValue()));
        System.out.println("----------------------------------------------------\n");
    }

    
    // Main – demonstration / test
    
    public static void main(String[] args) {

        ParkingGraph graph = new ParkingGraph();

        graph.addEdge("Entry_A",      "Junction_1",   10);
        graph.addEdge("Entry_A",      "Junction_2",   20);
        graph.addEdge("Junction_1",   "ParkingLot_B", 15);
        graph.addEdge("Junction_1",   "Junction_3",   12);
        graph.addEdge("Junction_2",   "Junction_3",   18);
        graph.addEdge("Junction_2",   "ParkingLot_A", 25);
        graph.addEdge("Junction_3",   "ParkingLot_C", 10);
        graph.addEdge("ParkingLot_B", "ParkingLot_C",  8);

        //  Display the graph 
        graph.displayGraph();

        //  Set parking availability 
        graph.markSlotAvailable("ParkingLot_A");
        graph.markSlotAvailable("ParkingLot_B");
        graph.markSlotAvailable("ParkingLot_C");

        //  Show all distances from Entry_A 
        graph.displayAllDistances("Entry_A");

        //  Navigate to nearest slot 
        graph.navigateToNearestSlot("Entry_A");

        //  Simulate ParkingLot_B getting occupied 
        System.out.println("\n ParkingLot_B is now occupied ");
        graph.markSlotOccupied("ParkingLot_B");
        graph.navigateToNearestSlot("Entry_A");

        //  Navigate from a different entry point 
        System.out.println("\n New driver arrives at Junction_2 ");
        graph.navigateToNearestSlot("Junction_2");
    }
}
