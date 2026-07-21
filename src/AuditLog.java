import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class AuditLog {

    private static final List<String> logs = new ArrayList<>();

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private AuditLog() {
    }

    public static void log(String username, String action) {

        if (username == null || username.isBlank()) {
            username = "UNKNOWN";
        }

        if (action == null || action.isBlank()) {
            action = "UNKNOWN ACTION";
        }

        String record =
                "[" + LocalDateTime.now().format(FORMATTER) + "] "
                        + "[" + username.trim().toLowerCase() + "] "
                        + action;

        logs.add(record);
    }

    public static List<String> getLogs() {
        return Collections.unmodifiableList(logs);
    }

    public static void displayLogs() {

        System.out.println("\n========== AUDIT LOG ==========");

        if (logs.isEmpty()) {
            System.out.println("No audit records found.");
            return;
        }

        for (String log : logs) {
            System.out.println(log);
        }
    }

    public static void clearLogs() {
        logs.clear();
    }

    public static int getLogCount() {
        return logs.size();
    }
}