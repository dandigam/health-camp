package health.camp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "camp")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Camp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String campName;

    @Column(nullable = false)
    private String organizerName;

    @Column(nullable = false)
    private String organizerPhone;

    @Column(nullable = true)
    private String organizerEmail;

    
    @Column(nullable = false)
    private Boolean active;
    // Location
    private Long stateId;
    private String state;
    private Long districtId;
    private String district;
    private Long mandalId;
    private String mandal;
    private String city;
    private String address;
    private String pinCode;
    

    @OneToMany(mappedBy = "camp", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DoctorCamp> doctorCamps;

    @OneToMany(mappedBy = "camp", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VolunteerCamp> volunteerCamps;
}
