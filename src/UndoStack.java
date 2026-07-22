import java.util.Stack;

public class UndoStack {

    private final Stack<Action> stack;

    public UndoStack() {
        stack = new Stack<>();
    }

    public void pushAction(ActionType type,
                           String plate,
                           String owner,
                           String slot) {

        stack.push(new Action(type, plate, owner, slot));
        System.out.println("Action saved.");
    }

    public Action undoAction() {

        if (stack.isEmpty()) {
            System.out.println("Nothing to undo.");
            return null;
        }

        Action action = stack.pop();

        System.out.println("Undo : "
                + action.getActionType()
                + " -> "
                + action.getLicensePlate());

        return action;
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int size() {
        return stack.size();
    }
}