package health.camp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lkp_conditions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LkpCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "condition_id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
