import java.util.List;

public class ParkingSystemMain {

    public static void main(String[] args) {

        System.out.println("======================================================");
        System.out.println("   UNIVERSITI MALAYA SMART PARKING MANAGEMENT SYSTEM  ");
        System.out.println("======================================================\n");

        //PHASE 1: SYSTEM INITIALIZATION AND MAP BUILDING
        System.out.println("---PHASE 1: INITIALIZING SYSTEMS AND MAP LAYOUT---\n");
        VehicleManagementSystem coreSystem = new VehicleManagementSystem();     //Role 1 (Linked List)
        VehicleQueue trafficQueue = new VehicleQueue();                         //Role 2 (Queue)
        UndoStack actionHistory= new UndoStack();                               //Role 2 (Stack)
        ParkingGraph campusMap = new ParkingGraph();                            //Role 3 (Graph)
        VehicleSearchSystem searchSystem = new VehicleSearchSystem();           //Role 4 (HashMap & BST)
        ParkingSlotAssignment prioritizer = new ParkingSlotAssignment();        //Role 5 (Priority Queue)

        //Building the campus map layout
        campusMap.addNode("Main_Gate");//driver will always start from the main gate
        campusMap.addNode("Central_Roundabout");
        campusMap.addNode("North_Wing");
        campusMap.addNode("East_Wing");

        campusMap.addEdge("Main_Gate","Central_Roundabout",10);
        campusMap.addEdge("Central_Roundabout","North_Wing",15);
        campusMap.addEdge("Central_Roundabout","East_Wing",25);

        campusMap.addEdge("North_Wing","Slot_N1",5);//Total distance = 30m
        campusMap.addEdge("North_Wing","Slot_N2",10);//Total distance = 35m
        campusMap.addEdge("East_Wing","Slot_E1",5);//Total distance = 40m

        //Display the graph layout and distances
        campusMap.displayGraph();
        campusMap.displayAllDistances("Main_Gate");

        //Priority Queue(Min-Heap) with the graph distances
        ParkingSlot slotN1=new ParkingSlot("Slot_N1",30);
        ParkingSlot slotN2=new ParkingSlot("Slot_N2",35);
        ParkingSlot slotE1=new ParkingSlot("Slot_E1",40);

        //add the parking slots in the prioritizer
        prioritizer.addParkingSlot(slotN1);
        prioritizer.addParkingSlot(slotN2);
        prioritizer.addParkingSlot(slotE1);

        //Tell the graph that these slots are available
        campusMap.markSlotAvailable("Slot_N1");
        campusMap.markSlotAvailable("Slot_N2");
        campusMap.markSlotAvailable("Slot_E1");

        //PHASE 2: VEHICLE ARRIVAL AND QUEUEING
        System.out.println("\n---PHASE 2: VEHICLE ARRIVING AT MAIN GATE---\n");

        //3 cars arriving at main gate
        trafficQueue.enqueue("WUV8888");
        trafficQueue.enqueue("BND2020");
        trafficQueue.enqueue("JQA1234");

        trafficQueue.displayQueue();

        //PHASE 3: PROCESSING AND SLOT ASSIGNMENT (CAR 1)
        System.out.println("\n---PHASE 3: PROCESSING THE FIRST VEHICLE---\n");

        String car1= trafficQueue.dequeue();//get the first car license plate
        String owner1="Ali";//owner name of first car

        if(car1!=null){
            //Min-heap assigns the closest slot (Slot_N1 at 30m)
            ParkingSlot assignedSlot=prioritizer.assignSlot();

            if(assignedSlot!=null){
                String slotId=assignedSlot.getSlotId();

                //Graph navigates to the assigned slot
                campusMap.navigateToNearestSlot("Main_Gate");

                //PHASE 4: REGISTRATION AND STORAGE
                System.out.println("\n---PHASE 4: REGISTRATION---\n");

                //Add vehicle into the core system(LinkedList)
                coreSystem.addVehicle(car1,owner1,slotId);
                //Add to Optimizer(HashMap, BST)
                searchSystem.addVehicle(car1,owner1,slotId);

                //Update graph and actionHistory
                campusMap.markSlotOccupied(slotId);
                actionHistory.pushAction("ADD",car1,owner1,slotId);
            }
        }

        //PHASE 5: PROCESSING THE SECOND CAR
        System.out.println("\n---PHASE 5: PROCESSING SECOND VEHICLE---\n");

        String car2= trafficQueue.dequeue();
        String owner2="Abu";

        if(car2!=null){
            //Min-Heap wll now assign to the next closest slot (N2 or E1)
            ParkingSlot assignedSlot=prioritizer.assignSlot();

            if(assignedSlot!=null){
                String slotId=assignedSlot.getSlotId();
                campusMap.navigateToNearestSlot("Main_Gate");

                coreSystem.addVehicle(car2,owner2,slotId);//add to core system
                searchSystem.addVehicle(car2,owner2,slotId);//add into search system
                campusMap.markSlotOccupied(slotId);//update the graph
                actionHistory.pushAction("ADD",car2,owner2,slotId);//update the action history
            }
        }

        //PHASE 6: SYSTEM SEARCH AND DISPLAY
        System.out.println("\n---PHASE 6: SEARCH AND SYSTEM STATE---\n");

        System.out.println("Testing O(1) Fast Search for WUV8888:");
        searchSystem.searchVehicleFast("WUV8888");

        System.out.println("\nTesting O(1) Fast Search for an invalid car:");
        searchSystem.searchVehicleFast("FAKE999");

        //Displaying vehicles in both formats
        coreSystem.displayVehicles();//Shows in normal order
        searchSystem.displayVehiclesSorted();//Shows in alphabetical order

        System.out.println("Total vehicles parked: "+coreSystem.getSize());

        //PHASE 7: DEPARTURE AND UNDO RECOVERY
        System.out.println("\n---PHASE 7: VEHICLE DEPARTURE AND UNDO---\n");

        //Ali decides to leave
        String leavingCar="WUV8888";
        VehicleNode removedNode=coreSystem.getVehicle(leavingCar);

        if(removedNode!=null){
            String freedSlotId= removedNode.parkingSlot;

            //Remove from linkedlist and searchSystem
            coreSystem.removeVehicle(leavingCar);
            searchSystem.removeVehicle(leavingCar);

            //Update action history stack
            actionHistory.pushAction("REMOVE", removedNode.licensePlate, removedNode.ownerName,  freedSlotId);

            //Release the slot back to the prioritizer
            prioritizer.releaseSlot(slotN1);
            campusMap.markSlotAvailable(freedSlotId);
        }

        System.out.println("\nWait that was a mistake! Undoing the last action...");
        Action lastAction=actionHistory.undoAction();

        if(lastAction!=null && lastAction.actionType.equalsIgnoreCase("REMOVE")){
            System.out.println("Restoring vehicle: "+lastAction.licensePlate);

            //Re-adding back everything
            coreSystem.addVehicle(lastAction.licensePlate, lastAction.ownerName,lastAction.parkingSlot);
            searchSystem.addVehicle(lastAction.licensePlate, lastAction.ownerName,lastAction.parkingSlot);

            campusMap.markSlotOccupied(lastAction.parkingSlot);
        }

        System.out.println("\n---FINAL SYSTEM STATE---\n");
        searchSystem.displayVehiclesSorted();

    }
}

