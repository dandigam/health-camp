package health.camp.entity;

import health.camp.model.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Patient extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_id", unique = true)
    private String patientId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "father_spouse_name")
    private String fatherSpouseName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "age")
    private Integer age;

    @Column(name = "marital_status")
    private String maritalStatus;  // SINGLE, MARRIED, DIVORCED, WIDOWED

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "photo_url")
    private String photoUrl;

    // ==================== Address ====================
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private Address address;

    // ==================== Medical History ====================
    @Column(name = "has_medical_history")
    private Boolean hasMedicalHistory;  // Has patient taken treatment at another hospital?

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "medical_history_id")
    private PatientOriginalHistory medicalHistory;

    // ==================== Payment Details ====================
    @Column(name = "payment_type")
    private String paymentType;  // FREE, PAID

    @Column(name = "payment_percentage")
    private Integer paymentPercentage;  // 25, 50, 75, 100 (only if PAID)

}
