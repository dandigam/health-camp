package health.camp.controller;

import health.camp.dto.auth.LoginRequest;
import health.camp.dto.auth.LoginResponse;
import health.camp.dto.user.UserResponse;
import health.camp.entity.User;
import health.camp.service.AuthService;
import health.camp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Auth", description = "Authentication APIs")
@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;


    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @Operation(summary = "Login")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(summary = "Current user")
    @GetMapping("/get")
    public ResponseEntity<List<User>> get() {
        return ResponseEntity.ok(authService.getAll());
    }

    @Operation(summary = "Current user")
    @GetMapping("/me")
    public ResponseEntity<UserResponse> me() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }
}
