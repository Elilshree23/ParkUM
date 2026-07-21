public class ParkingService {



    private final ParkingGraph graph;

    private final ParkingSlotAssignment assignment;



    public ParkingService(){


        graph =
                new ParkingGraph();


        assignment =
                new ParkingSlotAssignment();


    }



    public ParkingSlot assignSlot(){


        return assignment.assignSlot();


    }




    public void navigate(
            String location
    ){


        graph.navigateToNearestSlot(
                location
        );


    }



}
