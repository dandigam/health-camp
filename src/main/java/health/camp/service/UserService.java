package health.camp.service;

import health.camp.dto.user.UserResponse;
import health.camp.dto.user.UserUpdateRequest;
import health.camp.entity.User;
import health.camp.repository.UserRepository;
import health.camp.exception.ResourceNotFoundException;
import health.camp.security.AppUserDetailsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AppUserDetailsService userDetailsService;

    public UserService(UserRepository userRepository, AppUserDetailsService userDetailsService) {
        this.userRepository = userRepository;
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
    // All user settings logic removed (MongoDB legacy)
}
