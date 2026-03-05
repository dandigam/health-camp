package health.camp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "encounter_subject")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EncounterSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long id;

    @Column(name = "chief_complaint_text", columnDefinition = "TEXT")
    private String chiefComplaintText;

    @Column(name = "additional_notes", columnDefinition = "TEXT")
    private String additionalNotes;

    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "encounter_id")
    private Encounter encounter;

    @OneToMany(mappedBy = "encounterSubject", cascade = CascadeType.ALL)
    private Set<EncounterSymptom> symptoms;

    @OneToMany(mappedBy = "encounterSubject", cascade = CascadeType.ALL)
    private Set<EncounterCondition> conditions;

    @OneToMany(mappedBy = "encounterSubject", cascade = CascadeType.ALL)
    private Set<EncounterLifestyle> lifestyles;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}