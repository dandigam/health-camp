package health.camp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "encounter_symptoms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EncounterSymptom {

    @EmbeddedId
    private EncounterSymptomId id = new EncounterSymptomId();

    @ManyToOne
    @MapsId("subjectId")
    @JoinColumn(name = "subject_id")
    private EncounterSubject encounterSubject;

    @ManyToOne
    @MapsId("symptomId")
    @JoinColumn(name = "symptom_id")
    private LkpSymptom symptom;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EncounterSymptomId implements Serializable {
        private Long subjectId;
        private Long symptomId;
    }
}