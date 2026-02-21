package health.camp.dto.patient;

import health.camp.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponse {

    private Long id;
    private String patientId;
    private String firstName;
    private String lastName;
    private String fatherSpouseName;
    private Gender gender;
    private Integer age;
    private String maritalStatus;
    private String phoneNumber;
    private String photoUrl;

    // Address
    private AddressDto address;

    // Medical History
    private Boolean hasMedicalHistory;
    private MedicalHistoryDto medicalHistory;

    // Payment
    private String paymentType;
    private Integer paymentPercentage;

    // Audit
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddressDto {
        private Long id;
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
        private Long id;
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
        private Long id;
        private Long conditionId;
        private String conditionName;
        private String otherDetails;
    }
}
