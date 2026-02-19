package health.camp.controller;

import health.camp.dto.settings.UserSettingsRequest;
import health.camp.dto.settings.UserSettingsResponse;
import health.camp.dto.user.UserResponse;
import health.camp.dto.user.UserUpdateRequest;
import health.camp.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Users", description = "User profile and settings APIs")
@RestController
@RequestMapping("/api/users")
@Hidden
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Current user")
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMe() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @Operation(summary = "Update profile")
    @PatchMapping("/me")
    public ResponseEntity<UserResponse> updateMe(@Valid @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(userService.updateCurrentUser(request));
    }

    @Operation(summary = "Get settings")
    @GetMapping("/me/settings")
    public ResponseEntity<UserSettingsResponse> getSettings() {
        return ResponseEntity.ok(userService.getSettings());
    }

    @Operation(summary = "Update settings")
    @PatchMapping("/me/settings")
    public ResponseEntity<UserSettingsResponse> updateSettings(@RequestBody UserSettingsRequest request) {
        return ResponseEntity.ok(userService.updateSettings(request));
    }
}
