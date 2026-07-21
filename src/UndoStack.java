import java.util.Stack;

public class UndoStack {

    private final Stack<Action> stack;

    public UndoStack() {
        stack = new Stack<>();
    }

    public boolean pushAction(
            ActionType actionType,
            String licensePlate,
            String ownerName,
            String parkingSlot
    ) {

        if (actionType == null) {
            System.out.println(
                    "Error: Action type cannot be null."
            );
            return false;
        }

        if (licensePlate == null
                || licensePlate.isBlank()) {

            System.out.println(
                    "Error: License plate cannot be empty."
            );
            return false;
        }

        stack.push(
                new Action(
                        actionType,
                        licensePlate.trim().toUpperCase(),
                        ownerName,
                        parkingSlot
                )
        );

        System.out.println("Action saved.");

        return true;
    }

    public Action undoAction() {

        if (stack.isEmpty()) {

            System.out.println(
                    "No actions to undo."
            );

            return null;
        }

        Action lastAction = stack.pop();

        System.out.println(
                "Undoing: "
                        + lastAction.getActionType()
                        + " for vehicle "
                        + lastAction.getLicensePlate()
        );

        return lastAction;
    }
}