package health.camp.service;

import health.camp.dto.auth.LoginRequest;
import health.camp.dto.auth.LoginResponse;
import health.camp.dto.user.UserResponse;
import health.camp.model.User;
import health.camp.repository.UserRepository;
import health.camp.security.JwtService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {
        String emailOrMobile = request.getEmailOrMobile().trim();
        User user = userRepository.findByEmail(emailOrMobile)
                .or(() -> userRepository.findByPhone(emailOrMobile.replaceAll("\\D", "")))
                .orElseThrow(() -> new BadCredentialsException("Invalid email/mobile or password"));
        if (user.getPasswordHash() == null || !passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new BadCredentialsException("Invalid email/mobile or password");
        }
        String token = jwtService.createToken(user.getId());
        Instant expiresAt = Instant.now().plusMillis(86400000);
        UserResponse userResponse = toUserResponse(user);
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
