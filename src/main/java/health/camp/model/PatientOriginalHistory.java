package health.camp.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
@Data
@Document(collection = "patientOriginalHistory")
public class PatientOriginalHistory {

    private Long id;
    private Long patientId;
    private List<String> conditionsList;
    private String previousHospital;
    private String previousCurrentMedications;
    private String surgeryIllness;

    @CreatedBy
    private String createdBy;
    @CreatedDate
    private LocalDateTime createdAt;

}