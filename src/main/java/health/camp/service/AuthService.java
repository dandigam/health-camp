package health.camp.service;

import health.camp.dto.WareHouseDTO;
import health.camp.dto.auth.LoginRequest;
import health.camp.dto.auth.LoginResponse;
import health.camp.dto.user.UserResponse;
import health.camp.entity.RolePermission;
import health.camp.entity.User;
import health.camp.model.enums.UserRole;
import health.camp.repository.RolePermissionRepository;
import health.camp.repository.UserRepository;
import health.camp.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final WareHouseService wareHouseService;
    private final RolePermissionRepository rolePermissionRepository;


    public LoginResponse login(LoginRequest request) {

        String userName = request.getUserName().trim();

        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new BadCredentialsException("Invalid user or password"));

        // Password check (enable in production)
    /*
    if (user.getPasswordHash() == null ||
        !passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
        throw new BadCredentialsException("Invalid user or password");
    }
    */

        // Load permissions from DB
        List<RolePermission> rolePermissions =
                rolePermissionRepository.findByRole(user.getRole().name());

        List<String> permissions = rolePermissions.stream()
                .map(rp -> rp.getPermission().getName())
                .toList();

        // Create JWT with permissions
        String token = jwtService.createToken(user.getId(), user.getRole().name(), permissions);

        Instant expiresAt = Instant.now().plusMillis(86400000);

        UserResponse userResponse = toUserResponse(user);

        // Attach permissions in response (optional but recommended)
        userResponse.setPermissions(permissions);

        // Load warehouse data if applicable
        if (UserRole.WARE_HOUSE.equals(user.getRole())) {
            userResponse.setWareHouse(
                    wareHouseService.getWareHouseByUserId(user.getId())
            );
        }

        return new LoginResponse(token, expiresAt, userResponse);
    }


    public static UserResponse toUserResponse(User user) {
        UserResponse dto = new UserResponse();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole());
        dto.setAvatar(user.getAvatar());
        return dto;
    }


    public List<User> getAll() {
        return userRepository.findAll();
    }

}
