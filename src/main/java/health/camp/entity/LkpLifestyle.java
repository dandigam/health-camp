package health.camp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lkp_lifestyle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LkpLifestyle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lifestyle_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;
}
