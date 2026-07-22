public class ValidationUtil {

    private ValidationUtil() {
    }

    public static boolean isValidText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean isValidPlate(String plate) {

        if (!isValidText(plate))
            return false;

        return plate.matches("[A-Za-z0-9]{3,10}");
    }

    public static void requirePlate(String plate) {

        if (!isValidPlate(plate))
            throw new IllegalArgumentException("Invalid license plate.");
    }

    public static void requireText(String value, String field) {

        if (!isValidText(value))
            throw new IllegalArgumentException(field + " cannot be empty.");
    }
}