package health.camp.dto.settings;

import jakarta.validation.Valid;

public class UserSettingsRequest {

    @Valid
    private NotificationsDto notifications;
    @Valid
    private AppearanceDto appearance;
    @Valid
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
        private Boolean emailAlerts = true;
        private Boolean smsAlerts = false;
        private Boolean pushNotifications = true;
        private Boolean dailyDigest = false;

        public Boolean getEmailAlerts() {
            return emailAlerts;
        }

        public void setEmailAlerts(Boolean emailAlerts) {
            this.emailAlerts = emailAlerts;
        }

        public Boolean getSmsAlerts() {
            return smsAlerts;
        }

        public void setSmsAlerts(Boolean smsAlerts) {
            this.smsAlerts = smsAlerts;
        }

        public Boolean getPushNotifications() {
            return pushNotifications;
        }

        public void setPushNotifications(Boolean pushNotifications) {
            this.pushNotifications = pushNotifications;
        }

        public Boolean getDailyDigest() {
            return dailyDigest;
        }

        public void setDailyDigest(Boolean dailyDigest) {
            this.dailyDigest = dailyDigest;
        }
    }

    public static class AppearanceDto {
        private String theme = "light";
        private Boolean compactMode = false;
        private String language = "en";

        public String getTheme() {
            return theme;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }

        public Boolean getCompactMode() {
            return compactMode;
        }

        public void setCompactMode(Boolean compactMode) {
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
        private Boolean twoFactor = false;
        private String sessionTimeout = "30";

        public Boolean getTwoFactor() {
            return twoFactor;
        }

        public void setTwoFactor(Boolean twoFactor) {
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
