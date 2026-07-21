public final class AccessControl {

    private AccessControl() {
    }

    public static boolean canAddVehicle(User user) {
        return hasRole(user, UserRole.ADMIN, UserRole.STAFF);
    }

    public static boolean canRemoveVehicle(User user) {
        return hasRole(user, UserRole.ADMIN);
    }

    public static boolean canUpdateVehicle(User user) {
        return hasRole(user, UserRole.ADMIN, UserRole.STAFF);
    }

    public static boolean canSearchVehicle(User user) {
        return hasRole(user, UserRole.ADMIN, UserRole.STAFF);
    }

    public static boolean canViewLogs(User user) {
        return hasRole(user, UserRole.ADMIN);
    }

    public static boolean canManageUsers(User user) {
        return hasRole(user, UserRole.ADMIN);
    }

    private static boolean hasRole(User user, UserRole... roles) {

        if (user == null) {
            return false;
        }

        for (UserRole role : roles) {
            if (user.getRole() == role) {
                return true;
            }
        }

        return false;
    }
}
