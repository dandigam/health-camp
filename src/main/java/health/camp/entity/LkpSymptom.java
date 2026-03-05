package health.camp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lkp_symptoms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LkpSymptom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "symptom_id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

}
