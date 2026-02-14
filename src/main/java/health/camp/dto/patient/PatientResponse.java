package health.camp.dto.patient;

import health.camp.model.enums.Gender;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
public class PatientResponse {

    private Long id;
    private String patientId;
    private String campId;
    private String name;
    private String surname;
    private String fatherName;
    private int age;
    private Gender gender;
    private String phone;
    private String address;
    private String village;
    private String district;
    private String state;
    private String photoUrl;
    private LocalDateTime createdAt;

}
