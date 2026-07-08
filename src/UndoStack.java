import java.util.Stack;

public class UndoStack {
    private Stack<Action> stack;

    public UndoStack() {
        stack = new Stack<>();
    }

    // Save action
    public void pushAction(String actionType, String licensePlate, String ownerName, String parkingSlot) {
        stack.push(new Action(actionType, licensePlate, ownerName, parkingSlot));
        System.out.println("Action saved.");
    }

    // Undo latest action
    public Action undoAction() {
        if (stack.isEmpty()) {
            System.out.println("No actions to undo.");
            return null;
        }

        Action lastAction = stack.pop();
        System.out.println("Undoing: " + lastAction.actionType + " for vehicle " + lastAction.licensePlate);
        return lastAction;
    }
}
