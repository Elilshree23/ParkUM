import java.util.Stack;

public class UndoStack {

    private final Stack<Action> stack;

    public UndoStack() {
        stack = new Stack<>();
    }

    public boolean pushAction(
            ActionType actionType,
            String username,
            String licensePlate,
            String ownerName,
            String parkingSlot,
            String description
    ) {

        if (actionType == null) {
            System.out.println("Error: Action type cannot be null.");
            return false;
        }

        if (username == null || username.isBlank()) {
            System.out.println("Error: Username cannot be empty.");
            return false;
        }

        stack.push(
                new Action(
                        actionType,
                        username.trim().toLowerCase(),
                        licensePlate == null ? "" : licensePlate.trim().toUpperCase(),
                        ownerName == null ? "" : ownerName.trim(),
                        parkingSlot == null ? "" : parkingSlot.trim().toUpperCase(),
                        description == null ? "" : description.trim()
                )
        );

        return true;
    }

    public Action undoAction() {

        if (stack.isEmpty()) {

            System.out.println("No actions available to undo.");

            return null;
        }

        return stack.pop();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int size() {
        return stack.size();
    }

    public void clear() {
        stack.clear();
    }
}