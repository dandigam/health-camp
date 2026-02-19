package health.camp.service;

import health.camp.dto.settings.UserSettingsRequest;
import health.camp.dto.settings.UserSettingsResponse;
import health.camp.dto.user.UserResponse;
import health.camp.dto.user.UserUpdateRequest;
import health.camp.entity.User;
import health.camp.model.UserSettings;
import health.camp.repository.UserRepository;
import health.camp.repository.UserSettingsRepository;
import health.camp.exception.ResourceNotFoundException;
import health.camp.security.AppUserDetailsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserSettingsRepository userSettingsRepository;
    private final AppUserDetailsService userDetailsService;

    public UserService(UserRepository userRepository, UserSettingsRepository userSettingsRepository,
                       AppUserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.userSettingsRepository = userSettingsRepository;
        this.userDetailsService = userDetailsService;
    }

    public UserResponse getCurrentUser() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userDetailsService.loadUserEntityByUsername(userId);
        return AuthService.toUserResponse(user);
    }

    public UserResponse updateCurrentUser(UserUpdateRequest request) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", userId));
        if (request.getName() != null) user.setName(request.getName());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getPhone() != null) user.setPhone(request.getPhone());
        if (request.getPhotoUrl() != null) user.setAvatar(request.getPhotoUrl());
        user = userRepository.save(user);
        return AuthService.toUserResponse(user);
    }

    public UserSettingsResponse getSettings() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        UserSettings settings = userSettingsRepository.findByUserId(userId)
                .orElseGet(() -> {
                    UserSettings s = new UserSettings();
                    s.setId(UUID.randomUUID().toString());
                    s.setUserId(userId);
                    return userSettingsRepository.save(s);
                });
        return toSettingsResponse(settings);
    }

    public UserSettingsResponse updateSettings(UserSettingsRequest request) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        UserSettings settings = userSettingsRepository.findByUserId(userId)
                .orElseGet(() -> {
                    UserSettings s = new UserSettings();
                    s.setId(UUID.randomUUID().toString());
                    s.setUserId(userId);
                    return userSettingsRepository.save(s);
                });
        if (request.getNotifications() != null) {
            if (request.getNotifications().getEmailAlerts() != null) settings.setEmailAlerts(request.getNotifications().getEmailAlerts());
            if (request.getNotifications().getSmsAlerts() != null) settings.setSmsAlerts(request.getNotifications().getSmsAlerts());
            if (request.getNotifications().getPushNotifications() != null) settings.setPushNotifications(request.getNotifications().getPushNotifications());
            if (request.getNotifications().getDailyDigest() != null) settings.setDailyDigest(request.getNotifications().getDailyDigest());
        }
        if (request.getAppearance() != null) {
            if (request.getAppearance().getTheme() != null) settings.setTheme(request.getAppearance().getTheme());
            if (request.getAppearance().getCompactMode() != null) settings.setCompactMode(request.getAppearance().getCompactMode());
            if (request.getAppearance().getLanguage() != null) settings.setLanguage(request.getAppearance().getLanguage());
        }
        if (request.getSecurity() != null) {
            if (request.getSecurity().getTwoFactor() != null) settings.setTwoFactor(request.getSecurity().getTwoFactor());
            if (request.getSecurity().getSessionTimeout() != null) settings.setSessionTimeoutMinutes(Integer.parseInt(request.getSecurity().getSessionTimeout()));
        }
        settings = userSettingsRepository.save(settings);
        return toSettingsResponse(settings);
    }

    private UserSettingsResponse toSettingsResponse(UserSettings s) {
        UserSettingsResponse r = new UserSettingsResponse();
        UserSettingsResponse.NotificationsDto n = new UserSettingsResponse.NotificationsDto();
        n.setEmailAlerts(s.isEmailAlerts());
        n.setSmsAlerts(s.isSmsAlerts());
        n.setPushNotifications(s.isPushNotifications());
        n.setDailyDigest(s.isDailyDigest());
        r.setNotifications(n);
        UserSettingsResponse.AppearanceDto a = new UserSettingsResponse.AppearanceDto();
        a.setTheme(s.getTheme());
        a.setCompactMode(s.isCompactMode());
        a.setLanguage(s.getLanguage());
        r.setAppearance(a);
        UserSettingsResponse.SecurityDto sec = new UserSettingsResponse.SecurityDto();
        sec.setTwoFactor(s.isTwoFactor());
        sec.setSessionTimeout(String.valueOf(s.getSessionTimeoutMinutes()));
        r.setSecurity(sec);
        return r;
    }
}
