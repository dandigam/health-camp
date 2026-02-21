package health.camp.dto.patient;

import health.camp.model.enums.Gender;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientRequest {

    private Long id;  // If present, update; otherwise create

    @NotBlank(message = "firstName is required")
    @Size(min = 1, max = 100)
    private String firstName;

    @Size(max = 100)
    private String lastName;

    @Size(max = 100)
    private String fatherSpouseName;

    @NotNull(message = "gender is required")
    private Gender gender;

    @NotNull(message = "age is required")
    @Min(0)
    @Max(150)
    private Integer age;

    @Size(max = 50)
    private String maritalStatus;

    @Size(max = 20)
    private String phoneNumber;

    @Size(max = 2048)
    private String photoUrl;

    // Address
    @Valid
    private AddressDto address;

    // Medical History
    private Boolean hasMedicalHistory;

    @Valid
    private MedicalHistoryDto medicalHistory;

    // Payment
    private String paymentType;  // FREE, PAID
    private Integer paymentPercentage;  // 25, 50, 75, 100

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddressDto {
        private Long stateId;
        private String state;
        private Long districtId;
        private String district;
        private Long mandalId;
        private String mandal;
        private String cityVillage;
        private String streetAddress;
        private String pinCode;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MedicalHistoryDto {
        private List<MedicalConditionDto> conditions;
        private String previousHospitalName;
        private String currentMedications;
        private String pastSurgeryMajorIllness;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MedicalConditionDto {
        private Long conditionId;  // Reference to MedicalConditionLookup
        private String otherDetails;  // Additional details if needed
    }
}
