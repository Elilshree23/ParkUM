public class ParkingSystemMain {

    private static void printPhase(String title) {
        System.out.println();
        System.out.println("======================================================");
        System.out.println(title);
        System.out.println("======================================================\n");
    }


    public static void main(String[] args) {

        System.out.println("======================================================");
        System.out.println("   UNIVERSITI MALAYA SMART PARKING MANAGEMENT SYSTEM  ");
        System.out.println("======================================================\n");


        printPhase("PHASE 1 : INITIALIZING SYSTEMS AND MAP LAYOUT");


        VehicleManagementSystem coreSystem = new VehicleManagementSystem();
        VehicleQueue trafficQueue = new VehicleQueue();
        UndoStack actionHistory = new UndoStack();
        ParkingGraph campusMap = new ParkingGraph();
        VehicleSearchSystem searchSystem = new VehicleSearchSystem();
        ParkingSlotAssignment prioritizer = new ParkingSlotAssignment();



        campusMap.addNode("Main_Gate");
        campusMap.addNode("Central_Roundabout");
        campusMap.addNode("North_Wing");
        campusMap.addNode("East_Wing");


        campusMap.addEdge(
                "Main_Gate",
                "Central_Roundabout",
                10
        );

        campusMap.addEdge(
                "Central_Roundabout",
                "North_Wing",
                15
        );

        campusMap.addEdge(
                "Central_Roundabout",
                "East_Wing",
                25
        );

        campusMap.addEdge(
                "North_Wing",
                "Slot_N1",
                5
        );

        campusMap.addEdge(
                "North_Wing",
                "Slot_N2",
                10
        );

        campusMap.addEdge(
                "East_Wing",
                "Slot_E1",
                5
        );


        campusMap.displayGraph();
        campusMap.displayAllDistances("Main_Gate");



        ParkingSlot slotN1 = new ParkingSlot("Slot_N1", 30);
        ParkingSlot slotN2 = new ParkingSlot("Slot_N2", 35);
        ParkingSlot slotE1 = new ParkingSlot("Slot_E1", 40);



        prioritizer.addParkingSlot(slotN1);
        prioritizer.addParkingSlot(slotN2);
        prioritizer.addParkingSlot(slotE1);



        campusMap.markSlotAvailable("Slot_N1");
        campusMap.markSlotAvailable("Slot_N2");
        campusMap.markSlotAvailable("Slot_E1");



        printPhase("PHASE 2 : VEHICLE ARRIVAL AND QUEUEING");


        trafficQueue.enqueue("WUV8888");
        trafficQueue.enqueue("BND2020");
        trafficQueue.enqueue("JQA1234");


        trafficQueue.displayQueue();



        printPhase("PHASE 3 : PROCESSING FIRST VEHICLE");


        String car1 = trafficQueue.dequeue();
        String owner1 = "Ali";


        if (car1 != null) {


            ParkingSlot assignedSlot = prioritizer.assignSlot();


            if (assignedSlot != null) {


                String slotId = assignedSlot.getSlotId();


                campusMap.navigateToSlot(
                        "Main_Gate",
                        slotId
                );


                printPhase("PHASE 4 : VEHICLE REGISTRATION");


                coreSystem.addVehicle(
                        car1,
                        owner1,
                        slotId
                );


                searchSystem.addVehicle(
                        car1,
                        owner1,
                        slotId
                );


                campusMap.markSlotOccupied(slotId);


                actionHistory.pushAction(
                        ActionType.ADD,
                        car1,
                        owner1,
                        slotId
                );

            }
        }



        printPhase("PHASE 5 : PROCESSING SECOND VEHICLE");


        String car2 = trafficQueue.dequeue();
        String owner2 = "Abu";


        if (car2 != null) {


            ParkingSlot assignedSlot = prioritizer.assignSlot();


            if (assignedSlot != null) {


                String slotId = assignedSlot.getSlotId();


                campusMap.navigateToSlot(
                        "Main_Gate",
                        slotId
                );


                coreSystem.addVehicle(
                        car2,
                        owner2,
                        slotId
                );


                searchSystem.addVehicle(
                        car2,
                        owner2,
                        slotId
                );


                campusMap.markSlotOccupied(slotId);


                actionHistory.pushAction(
                        ActionType.ADD,
                        car2,
                        owner2,
                        slotId
                );

            }

        }




        printPhase("PHASE 6 : SEARCH AND SYSTEM STATE");



        System.out.println("Testing O(1) Fast Search for WUV8888:");

        searchSystem.searchVehicleFast("WUV8888");



        System.out.println("\nTesting O(1) Fast Search for invalid vehicle:");

        searchSystem.searchVehicleFast("FAKE999");



        coreSystem.displayVehicles();


        searchSystem.displayVehiclesSorted();



        System.out.println(
                "Total vehicles parked : "
                        + coreSystem.getSize()
        );





        printPhase("PHASE 7 : VEHICLE DEPARTURE AND UNDO RECOVERY");



        String leavingCar = "WUV8888";


        VehicleNode removedNode =
                coreSystem.getVehicle(leavingCar);



        if (removedNode != null) {


            String freedSlotId =
                    removedNode.getParkingSlot();



            coreSystem.removeVehicle(leavingCar);


            searchSystem.removeVehicle(leavingCar);



            actionHistory.pushAction(
                    ActionType.REMOVE,
                    removedNode.getLicensePlate(),
                    removedNode.getOwnerName(),
                    freedSlotId
            );



            prioritizer.releaseSlot(slotN1);


            campusMap.markSlotAvailable(
                    freedSlotId
            );

        }




        System.out.println(
                "\nMistake detected! Undoing last action..."
        );



        Action lastAction =
                actionHistory.undoAction();




        if(lastAction != null &&
                lastAction.getActionType() == ActionType.REMOVE) {



            System.out.println(
                    "Restoring vehicle : "
                            + lastAction.getLicensePlate()
            );



            coreSystem.addVehicle(
                    lastAction.getLicensePlate(),
                    lastAction.getOwnerName(),
                    lastAction.getParkingSlot()
            );



            searchSystem.addVehicle(
                    lastAction.getLicensePlate(),
                    lastAction.getOwnerName(),
                    lastAction.getParkingSlot()
            );



            campusMap.markSlotOccupied(
                    lastAction.getParkingSlot()
            );


            prioritizer.assignSlot();

        }





        printPhase("FINAL SYSTEM STATE");


        searchSystem.displayVehiclesSorted();

        campusMap.displayParkingStatus();

    }
}