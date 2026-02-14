package health.camp.model;

import health.camp.model.enums.Gender;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

@Document(collection = "patients")
@CompoundIndexes({
        @CompoundIndex(name = "camp_patientId", def = "{'campId': 1, 'patientId': 1}", unique = true)
})
@Data
public class Patient extends Location {

    @Id
    private Long id;
    @Indexed
    private String patientId;
    @Indexed
    private String campId;
    private String name;
    private String surname;
    private String fatherName;
    private int age;
    private Gender gender;
    private String phone;
    private String photoUrl;
    private String maritalStatus;
    private boolean isPreviousMedicalHistory;

    @CreatedBy
    private String createdBy;
    @CreatedDate
    private LocalDateTime createdAt;

}
