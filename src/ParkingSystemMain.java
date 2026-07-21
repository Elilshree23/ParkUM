public class ParkingSystemMain {

    public static void main(
            String[] args
    ) {

        System.out.println(
                "======================================================"
        );

        System.out.println(
                "   UNIVERSITI MALAYA SMART PARKING MANAGEMENT SYSTEM  "
        );

        System.out.println(
                "======================================================\n"
        );

        System.out.println(
                "---PHASE 1: INITIALIZING SYSTEMS "
                        + "AND MAP LAYOUT---\n"
        );

        VehicleManagementSystem coreSystem =
                new VehicleManagementSystem();

        VehicleQueue trafficQueue =
                new VehicleQueue();

        UndoStack actionHistory =
                new UndoStack();

        ParkingGraph campusMap =
                new ParkingGraph();

        VehicleSearchSystem searchSystem =
                new VehicleSearchSystem();

        ParkingSlotAssignment prioritizer =
                new ParkingSlotAssignment();

        campusMap.addNode(
                "Main_Gate"
        );

        campusMap.addNode(
                "Central_Roundabout"
        );

        campusMap.addNode(
                "North_Wing"
        );

        campusMap.addNode(
                "East_Wing"
        );

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

        campusMap.displayAllDistances(
                "Main_Gate"
        );

        ParkingSlot slotN1 =
                new ParkingSlot(
                        "Slot_N1",
                        30
                );

        ParkingSlot slotN2 =
                new ParkingSlot(
                        "Slot_N2",
                        35
                );

        ParkingSlot slotE1 =
                new ParkingSlot(
                        "Slot_E1",
                        40
                );

        prioritizer.addParkingSlot(
                slotN1
        );

        prioritizer.addParkingSlot(
                slotN2
        );

        prioritizer.addParkingSlot(
                slotE1
        );

        campusMap.markSlotAvailable(
                "Slot_N1"
        );

        campusMap.markSlotAvailable(
                "Slot_N2"
        );

        campusMap.markSlotAvailable(
                "Slot_E1"
        );

        System.out.println(
                "\n---PHASE 2: VEHICLE ARRIVING "
                        + "AT MAIN GATE---\n"
        );

        trafficQueue.enqueue(
                "WUV8888"
        );

        trafficQueue.enqueue(
                "BND2020"
        );

        trafficQueue.enqueue(
                "JQA1234"
        );

        trafficQueue.displayQueue();

        System.out.println(
                "\n---PHASE 3: PROCESSING "
                        + "THE FIRST VEHICLE---\n"
        );

        String car1 =
                trafficQueue.dequeue();

        String owner1 =
                "Ali";

        if (car1 != null) {

            ParkingSlot assignedSlot =
                    prioritizer.assignSlot();

            if (assignedSlot != null) {

                String slotId =
                        assignedSlot.getSlotId();

                campusMap.navigateToNearestSlot(
                        "Main_Gate"
                );

                System.out.println(
                        "\n---PHASE 4: REGISTRATION---\n"
                );

                boolean addedToCore =
                        coreSystem.addVehicle(
                                car1,
                                owner1,
                                slotId
                        );

                if (addedToCore) {

                    boolean addedToSearch =
                            searchSystem.addVehicle(
                                    car1,
                                    owner1,
                                    slotId
                            );

                    if (addedToSearch) {

                        boolean slotOccupied =
                                campusMap.markSlotOccupied(
                                        slotId
                                );

                        if (slotOccupied) {

                            actionHistory.pushAction(
                                    ActionType.ADD,
                                    car1,
                                    owner1,
                                    slotId
                            );

                        } else {

                            System.out.println(
                                    "Error: Slot state update failed."
                            );
                        }

                    } else {

                        System.out.println(
                                "Error: Search system "
                                        + "rejected vehicle."
                        );
                    }

                } else {

                    System.out.println(
                            "Skipping registration — "
                                    + "vehicle rejected."
                    );
                }
            }
        }

        System.out.println(
                "\n---PHASE 5: PROCESSING "
                        + "SECOND VEHICLE---\n"
        );

        String car2 =
                trafficQueue.dequeue();

        String owner2 =
                "Abu";

        if (car2 != null) {

            ParkingSlot assignedSlot =
                    prioritizer.assignSlot();

            if (assignedSlot != null) {

                String slotId =
                        assignedSlot.getSlotId();

                campusMap.navigateToNearestSlot(
                        "Main_Gate"
                );

                boolean addedToCore =
                        coreSystem.addVehicle(
                                car2,
                                owner2,
                                slotId
                        );

                if (addedToCore) {

                    boolean addedToSearch =
                            searchSystem.addVehicle(
                                    car2,
                                    owner2,
                                    slotId
                            );

                    if (addedToSearch) {

                        boolean slotOccupied =
                                campusMap.markSlotOccupied(
                                        slotId
                                );

                        if (slotOccupied) {

                            actionHistory.pushAction(
                                    ActionType.ADD,
                                    car2,
                                    owner2,
                                    slotId
                            );

                        } else {

                            System.out.println(
                                    "Error: Slot state update failed."
                            );
                        }

                    } else {

                        System.out.println(
                                "Error: Search system "
                                        + "rejected vehicle."
                        );
                    }
                }
            }
        }

        System.out.println(
                "\n---PHASE 6: SEARCH "
                        + "AND SYSTEM STATE---\n"
        );

        System.out.println(
                "Testing O(1) Fast Search for WUV8888:"
        );

        searchSystem.searchVehicleFast(
                "WUV8888"
        );

        System.out.println(
                "\nTesting O(1) Fast Search "
                        + "for an invalid car:"
        );

        searchSystem.searchVehicleFast(
                "FAKE999"
        );

        coreSystem.displayVehicles();

        searchSystem.displayVehiclesSorted();

        System.out.println(
                "Total vehicles parked: "
                        + coreSystem.getSize()
        );

        System.out.println(
                "\n---PHASE 7: VEHICLE DEPARTURE "
                        + "AND UNDO---\n"
        );

        String leavingCar =
                "WUV8888";

        VehicleNode removedNode =
                coreSystem.getVehicle(
                        leavingCar
                );

        if (removedNode != null) {

            String freedSlotId =
                    removedNode.getParkingSlot();

            boolean removedFromCore =
                    coreSystem.removeVehicle(
                            leavingCar
                    );

            boolean removedFromSearch =
                    searchSystem.removeVehicle(
                            leavingCar
                    );

            if (removedFromCore
                    && removedFromSearch) {

                actionHistory.pushAction(
                        ActionType.REMOVE,
                        removedNode.getLicensePlate(),
                        removedNode.getOwnerName(),
                        freedSlotId
                );

                prioritizer.releaseSlot(
                        slotN1
                );

                campusMap.markSlotAvailable(
                        freedSlotId
                );

            } else {

                System.out.println(
                        "Error: Vehicle removal "
                                + "was not completed consistently."
                );
            }
        }

        System.out.println(
                "\nWait that was a mistake! "
                        + "Undoing the last action..."
        );

        Action lastAction =
                actionHistory.undoAction();

        if (lastAction != null
                && lastAction.getActionType()
                == ActionType.REMOVE) {

            System.out.println(
                    "Restoring vehicle: "
                            + lastAction.getLicensePlate()
            );

            boolean restoredToCore =
                    coreSystem.addVehicle(
                            lastAction.getLicensePlate(),
                            lastAction.getOwnerName(),
                            lastAction.getParkingSlot()
                    );

            if (restoredToCore) {

                boolean restoredToSearch =
                        searchSystem.addVehicle(
                                lastAction.getLicensePlate(),
                                lastAction.getOwnerName(),
                                lastAction.getParkingSlot()
                        );

                if (restoredToSearch) {

                    campusMap.markSlotOccupied(
                            lastAction.getParkingSlot()
                    );

                } else {

                    System.out.println(
                            "Restore failed — "
                                    + "search system rejected vehicle."
                    );
                }

            } else {

                System.out.println(
                        "Restore failed — vehicle "
                                + "could not be re-added."
                );
            }
        }

        System.out.println(
                "\n---FINAL SYSTEM STATE---\n"
        );

        searchSystem.displayVehiclesSorted();
    }
}