import java.time.LocalDateTime;
import java.util.ArrayList;


public class AuditLog {


    private static final ArrayList<String>
            logs =
            new ArrayList<>();




    public static void log(
            String username,
            String action
    ){


        String record =
                LocalDateTime.now()
                        +
                        " | USER: "
                        +
                        username
                        +
                        " | ACTION: "
                        +
                        action;



        logs.add(record);


    }





    public static void displayLogs(){


        System.out.println(
                "\n===== AUDIT LOG ====="
        );


        for(String log : logs){


            System.out.println(log);


        }


    }



}