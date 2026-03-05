package health.camp.entity;

import health.camp.model.enums.EncounterStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "encounters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Encounter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Patient who came
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    // Camp where visit happened
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "camp_event_id", nullable = false)
    private CampEvent campEvent;

    // Assigned doctor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    // Queue status
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EncounterStatus status;

    // Token number for queue
    @Column(name = "token_number")
    private Integer tokenNumber;

    // When encounter created
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
