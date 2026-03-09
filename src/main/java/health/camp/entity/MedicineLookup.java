package health.camp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "medicine_lookup")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MedicineLookup extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medicine_id")
    private Long medicineId;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "strength", length = 50)
    private String strength;

    // Unit (e.g., mg, g, ml)
    @Column(name = "unit", length = 20)
    private String unit;

    @Column(name = "manufacturer", length = 100)
    private String manufacturer;
}
