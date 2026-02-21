package health.camp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Address extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "state_id")
    private Long stateId;

    @Column(name = "state")
    private String state;

    @Column(name = "district_id")
    private Long districtId;

    @Column(name = "district")
    private String district;

    @Column(name = "mandal_id")
    private Long mandalId;

    @Column(name = "mandal")
    private String mandal;

    @Column(name = "city_village")
    private String cityVillage;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "pin_code")
    private String pinCode;
}
