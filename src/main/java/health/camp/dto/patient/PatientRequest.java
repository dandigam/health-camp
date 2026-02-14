package health.camp.dto.patient;

import health.camp.model.enums.Gender;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PatientRequest {

    @NotBlank(message = "campId is required")
    private String campId;

    @NotBlank(message = "name is required")
    @Size(min = 1, max = 100)
    private String name;

    @Size(max = 100)
    private String surname;

    @NotBlank(message = "fatherName is required")
    @Size(max = 100)
    private String fatherName;

    @NotNull(message = "gender is required")
    private Gender gender;

    @NotNull(message = "age is required")
    @Min(0)
    @Max(150)
    private Integer age;

    @Size(max = 20)
    private String phone;

    @Size(max = 500)
    private String address;

    @NotBlank(message = "village is required")
    @Size(max = 100)
    private String village;

    @Size(max = 100)
    private String district;

    @Size(max = 100)
    private String state;

    @Size(max = 50)
    private String maritalStatus;

    @Size(max = 2048)
    private String photoUrl;

    private boolean isPreviousMedicalHistory;


}
