package health.camp.dto.settings;

public class UserSettingsResponse {

    private NotificationsDto notifications;
    private AppearanceDto appearance;
    private SecurityDto security;

    public NotificationsDto getNotifications() {
        return notifications;
    }

    public void setNotifications(NotificationsDto notifications) {
        this.notifications = notifications;
    }

    public AppearanceDto getAppearance() {
        return appearance;
    }

    public void setAppearance(AppearanceDto appearance) {
        this.appearance = appearance;
    }

    public SecurityDto getSecurity() {
        return security;
    }

    public void setSecurity(SecurityDto security) {
        this.security = security;
    }

    public static class NotificationsDto {
        private boolean emailAlerts = true;
        private boolean smsAlerts = false;
        private boolean pushNotifications = true;
        private boolean dailyDigest = false;

        public boolean isEmailAlerts() {
            return emailAlerts;
        }

        public void setEmailAlerts(boolean emailAlerts) {
            this.emailAlerts = emailAlerts;
        }

        public boolean isSmsAlerts() {
            return smsAlerts;
        }

        public void setSmsAlerts(boolean smsAlerts) {
            this.smsAlerts = smsAlerts;
        }

        public boolean isPushNotifications() {
            return pushNotifications;
        }

        public void setPushNotifications(boolean pushNotifications) {
            this.pushNotifications = pushNotifications;
        }

        public boolean isDailyDigest() {
            return dailyDigest;
        }

        public void setDailyDigest(boolean dailyDigest) {
            this.dailyDigest = dailyDigest;
        }
    }

    public static class AppearanceDto {
        private String theme = "light";
        private boolean compactMode = false;
        private String language = "en";

        public String getTheme() {
            return theme;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }

        public boolean isCompactMode() {
            return compactMode;
        }

        public void setCompactMode(boolean compactMode) {
            this.compactMode = compactMode;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }
    }

    public static class SecurityDto {
        private boolean twoFactor = false;
        private String sessionTimeout = "30";

        public boolean isTwoFactor() {
            return twoFactor;
        }

        public void setTwoFactor(boolean twoFactor) {
            this.twoFactor = twoFactor;
        }

        public String getSessionTimeout() {
            return sessionTimeout;
        }

        public void setSessionTimeout(String sessionTimeout) {
            this.sessionTimeout = sessionTimeout;
        }
    }
}
