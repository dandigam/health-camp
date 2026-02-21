package health.camp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medical_condition_lookup")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalConditionLookup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;  // Diabetes, Hypertension, Heart Disease, Asthma/Breathing Issues, Thyroid, Other

    @Column(name = "description")
    private String description;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "display_order")
    private Integer displayOrder;
}
