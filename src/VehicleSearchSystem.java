import java.util.HashMap;

//A new node to build BST
class BSTNode{
    VehicleNode vehicle;
    BSTNode left;
    BSTNode right;

    public BSTNode(VehicleNode vehicle){
        this.vehicle=vehicle;
        this.left=null;
        this.right=null;
    }
}


public class VehicleSearchSystem {

    // HashMap for fast retrieval
    private HashMap<String, VehicleNode> vehicleMap;

    // BST for sorting and searching
    private BSTNode root;

    public VehicleSearchSystem() {
        vehicleMap = new HashMap<>();
        root=null;
    }

    // Add vehicle record to both data structures
    public void addVehicle(String licensePlate, String ownerName, String parkingSlot) {
        VehicleNode vehicle= new VehicleNode(licensePlate, ownerName, parkingSlot);
        vehicleMap.put(licensePlate, vehicle);//Add the vehicle to the HashMap
        root=insertIntoBST(root,vehicle);//Add to BST
        System.out.println("Vehicle added to the Optimizer System.");
    }

    //Recursive BST insert (sorts automatically by license plate)
    private BSTNode insertIntoBST(BSTNode node, VehicleNode vehicle) {
        if(node==null){
            return new BSTNode(vehicle);
        }

        //Compare license plates alphabetically to decide left or right branch
        if(vehicle.licensePlate.compareToIgnoreCase(node.vehicle.licensePlate)<0){
            node.left=insertIntoBST(node.left,vehicle);
        }
        else if(vehicle.licensePlate.compareToIgnoreCase(node.vehicle.licensePlate)>0){
            node.right=insertIntoBST(node.right,vehicle);
        }
        return node;
    }

    // Search by plate number
    public void searchVehicleFast(String licensePlate) {
        VehicleNode found = vehicleMap.get(licensePlate);
        if(found != null) {
            System.out.println("\nVehicle Found!");
            System.out.println("License Plate: "+found.licensePlate+" | Owner: "+found.ownerName+" | Parking Slot: "+found.parkingSlot);

        }
        else {
            System.out.println("\nVehicle not found.");
        }
    }

    //Display records sorted alphabetically using BST In-Order Traversal
    public void displayVehiclesSorted(){
        System.out.println("\n--- Vehicles Records (Sorted Alphabetically) ---");
        if(root==null){
            System.out.println("No vehicles in the system.");
        }
        else{
            inOrderTraversal(root);
        }
    }

    //Helper method for sorting (Visit left, root, right)
    private void inOrderTraversal(BSTNode node){
        if(node!=null){
            inOrderTraversal(node.left);
            System.out.println("License Plate: "+node.vehicle.licensePlate+" | Owner: "+node.vehicle.ownerName+" | Parking Slot: "+node.vehicle.parkingSlot);
            inOrderTraversal(node.right);
        }
    }

    // Remove vehicle from both data structures
    public void removeVehicle(String licensePlate) {
        if(vehicleMap.containsKey(licensePlate)) {
            vehicleMap.remove(licensePlate);//remove from the HashMap
            root=deleteFromBST(root,licensePlate);//remove from BST
            System.out.println("Vehicle removed from the Optimizer System.");
        }
        else {
            System.out.println("Vehicle does not exist.");
        }
    }

    //Recursive delete from BST
    private BSTNode deleteFromBST(BSTNode node, String licensePlate) {
        if(node==null){
            return null;
        }
        int compareValue = node.vehicle.licensePlate.compareToIgnoreCase(licensePlate);

        if(compareValue<0){
            node.left=deleteFromBST(node.left,licensePlate);
        }
        else if(compareValue>0){
            node.right=deleteFromBST(node.right,licensePlate);
        }
        else{
            //Node to delete is found

            //Case 1: One child or no child
            if(node.left==null){
                return node.right;
            }
            if(node.right==null){
                return node.left;
            }

            //Case 2: Node with two children
            //Find the smallest node in the right subtree
            BSTNode minNode=findMin(node.right);
            node.vehicle=minNode.vehicle;//Replace value
            node.right=deleteFromBST(node.right,minNode.vehicle.licensePlate);//Delete the duplicate
        }
        return node;
    }

    //Helper method to find the smallest node
    private BSTNode findMin(BSTNode node){
        while(node.left!=null){
            node=node.left;
        }
        return node;
    }
}
