package health.camp.model.enums;

public enum UserRole {

    SUPER_ADMIN("Super Admin"),
    CAMP_ADMIN("Camp Admin"),
    DOCTOR("Doctor"),
    NURSE("Nurse"),
    PHARMACY("Pharmacy"),
    WAREHOUSE("Warehouse"),
    FRONT_DESK("Front Desk"),
    ADMIN("Admin");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}