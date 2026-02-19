package health.camp.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "emailOrMobile is required")
    private String userName;

    @NotBlank(message = "password is required")
    private String password;

}
