package health.camp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "patient_original_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PatientOriginalHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Dynamic conditions from lookup - selected by user
    @OneToMany(mappedBy = "patientHistory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PatientMedicalCondition> conditions;

    @Column(name = "previous_hospital_name")
    private String previousHospitalName;

    @Column(name = "current_medications", columnDefinition = "TEXT")
    private String currentMedications;

    @Column(name = "past_surgery_major_illness", columnDefinition = "TEXT")
    private String pastSurgeryMajorIllness;
}
