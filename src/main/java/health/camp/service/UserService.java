package health.camp.service;

import health.camp.dto.user.UserRegisterRequest;
import health.camp.dto.user.UserResponse;
import health.camp.dto.user.UserUpdateRequest;
import health.camp.entity.User;
import health.camp.repository.UserRepository;
import health.camp.exception.ResourceNotFoundException;
import health.camp.model.enums.UserRole;
import health.camp.security.AppUserDetailsService;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
private UserResponse toResponse(User user) {

    return UserResponse.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .phone(user.getPhone())
            .role(user.getRole())
            .roleDisplayName(user.getRole().getDisplayName())
            .avatar(user.getAvatar())
           
            .build();
}
    public UserResponse registerUser(UserRegisterRequest request) {
      
        User user = new User();
        user.setName(request.getFullName());
        user.setUserName(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhoneNumber());
        user.setPasswordHash(request.getPassword()); 
        user.setRole(UserRole.valueOf(request.getRole()));
        user = userRepository.save(user);
        return toResponse(user);
    }

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
