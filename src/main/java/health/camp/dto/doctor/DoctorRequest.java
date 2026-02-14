package health.camp.dto.doctor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
@Data
public class DoctorRequest {

    @NotBlank(message = "name is required")
    @Size(min = 1, max = 255)
    private String name;

    @NotBlank(message = "specialization is required")
    @Size(max = 100)
    private String specialization;

    @NotBlank(message = "phone is required")
    @Size(max = 20)
    private String phone;

    @jakarta.validation.constraints.Email(message = "invalid email format")
    @Size(max = 255)
    private String email;

    @Size(max = 2048)
    private String photoUrl;

    private Boolean active;

    private List<String> campIds;
}
