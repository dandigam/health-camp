package health.camp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "doctor_camp")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorCamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "camp_id")
    private Camp camp;

    @Column(name = "is_default")
    private Boolean isDefault = false;

    @Column(name = "active")
    private Boolean active = false;
}
