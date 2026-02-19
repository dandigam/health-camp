package health.camp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mandals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mandal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "district_id", nullable = false)
    private District district;
}
