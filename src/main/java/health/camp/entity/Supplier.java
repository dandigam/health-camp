package health.camp.entity;

import health.camp.model.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "suppliers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Supplier extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private Long supplierId;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private WareHouse warehouse;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "contact_number", length = 20)
    private String contactNumber;

    @Column(name = "state_id")
    private Long stateId;

    @Column(name = "district_id")
    private Long districtId;

    @Column(name = "mandal_id")
    private Long mandalId;

    @Column(name = "city", length = 50)
    private String city;

    @Column(name = "pin_code", length = 10)
    private String pinCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 10)
    private Status status;

    @Column(name = "email", length = 100)
    private String email;
}
