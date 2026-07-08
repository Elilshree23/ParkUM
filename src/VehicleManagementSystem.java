public class VehicleManagementSystem {
    VehicleNode head;//first node of the list
    VehicleNode tail;//last node of the list
    private int size;//size of the list (number of vehicles)

    public VehicleManagementSystem() {
        head = null;
        tail = null;
        size = 0;
    }

    //Add operation: Adding a new vehicle record to the back of the list
    public void addVehicle(String licensePlate, String ownerName, String parkingSlot) {
        VehicleNode newNode = new VehicleNode(licensePlate, ownerName, parkingSlot);
        //if no vehicles in the list
        if (head == null) {
            //both head and tail are pointing to the new node
            head = newNode;
            tail = newNode;
        }
        else{
            tail.next=newNode;//placing the new vehicle at the back of the list
            tail=newNode;//update the new tail
        }
        size=size+1;//increments the size
        System.out.println("Added: Vehicle "+licensePlate+" at "+parkingSlot);
    }

    //Remove operation: Finds and removes a vehicle by its license plate
    public boolean removeVehicle(String licensePlate){
        //if no vehicles in the list
        if(head == null){
            System.out.println("System is empty. No vehicles to remove.");
            return false;
        }
        //if the vehicle node to be removed is at the head
        else if(head.licensePlate.equalsIgnoreCase(licensePlate)){
            head = head.next;//update the new head
            if(head == null){
                tail = null;//set tail null as well since list is empty
            }
            size=size-1;//reduce the size
            System.out.println("Removed: Vehicle "+licensePlate);//print success message
            return true;//return true
        }
        //if the vehicle node to be removed is at the tail
        else if(tail.licensePlate.equalsIgnoreCase(licensePlate)){
            VehicleNode current=head;
            for(int i=0;i<size-2;i++){
                current=current.next;//make the current pointer reach node before the tail node
            }
            tail = current;//update the new tail
            tail.next = null;
            size=size-1;//reduce the size
            System.out.println("Removed: Vehicle "+licensePlate);//print success message
            return true;//return true
        }
        //if the vehicle node to be removed is in between at the list
        VehicleNode current=head.next;
        VehicleNode previous=head;

        while(current!=null){
            if(current.licensePlate.equalsIgnoreCase(licensePlate)){
                break;//break the loop if a match found
            }
            previous=current;
            current=current.next;//current pointer is one node ahead of previous pointer
        }
        if(current==null){//if no matching license plate found
            System.out.println("Error: Vehicle "+licensePlate+" does not exist.");
            return false;
        }
        previous.next=current.next;//unlink the chain and connect it back after removing the specific vehicle node
        size=size-1;//reduce size
        System.out.println("Removed: Vehicle "+licensePlate);
        return true;
    }

    //Display operation: Prints all the currently parked vehicles record
    public void displayVehicles(){
        //if the list is empty
        if(head==null){
            System.out.println("System is empty. No vehicles can be displayed.");
            return;//end the method immediately
        }

        System.out.println("---Current Vehicles Record---");
        VehicleNode current=head;
        while(current!=null){
            System.out.println("License Plate: "+current.licensePlate+" | Owner: "+current.ownerName+" | Parking Slot: "+current.parkingSlot);
            current=current.next;//moving the pointer to traverse the entire list
        }
        System.out.println();
    }

    //Helper methods

    //Getter method for size
    public int getSize(){
        return size;
    }

    //Search vehicle method by using the license plate
    public VehicleNode getVehicle(String licensePlate){
        VehicleNode current=head;
        while(current!=null){//traverse the entire list
            if(current.licensePlate.equalsIgnoreCase(licensePlate)){//compare the license plate without case sensitive
                return current;//return the vehicle node that matches the license plate
            }
            current=current.next;//moving the pointer
        }
        return null;//return null if no match is found
    }
}
