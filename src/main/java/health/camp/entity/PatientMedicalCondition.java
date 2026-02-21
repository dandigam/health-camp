package health.camp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "patient_medical_conditions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientMedicalCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_history_id", nullable = false)
    private PatientOriginalHistory patientHistory;

    @ManyToOne
    @JoinColumn(name = "condition_id", nullable = false)
    private MedicalConditionLookup condition;

    @Column(name = "other_details")
    private String otherDetails;  // Only used when condition is "Other"
}
