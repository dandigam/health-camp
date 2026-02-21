package health.camp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "warehouses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class WareHouse extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "email", unique = true, length = 255)
    private String email;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "authorized_person", length = 255)
    private String authorizedPerson;

    @Column(name = "licence_number", unique = true, length = 100)
    private String licenceNumber;

    // Address Fields
    @Column(name = "state", length = 100)
    private String state;

    @Column(name = "district", length = 100)
    private String district;

    @Column(name = "mandal", length = 100)
    private String mandal;

    @Column(name = "village", length = 100)
    private String village;

    @Column(name = "pin_code", length = 20)
    private String pinCode;

    @Column(name = "user_id")
    private Long userId;
}
