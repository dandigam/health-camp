package health.camp.controller;

import health.camp.dto.user.UserRegisterRequest;
import health.camp.dto.user.UserResponse;
import health.camp.dto.user.UserUpdateRequest;
import health.camp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Users", description = "User profile and settings APIs")
@RestController
@RequestMapping("/api/users")
@CrossOrigin("*") 
@AllArgsConstructor
public class UserController {
    
    private final UserService userService;

    
    @Operation(summary = "Register new user")
    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRegisterRequest request) {
        return ResponseEntity.ok(userService.registerUser(request));
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

}
 
