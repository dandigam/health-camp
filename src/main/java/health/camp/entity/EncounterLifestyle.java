package health.camp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "encounter_lifestyle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EncounterLifestyle {

    @EmbeddedId
    private EncounterLifestyleId id = new EncounterLifestyleId();

    @ManyToOne
    @MapsId("subjectId")
    @JoinColumn(name = "subject_id")
    private EncounterSubject encounterSubject;

    @ManyToOne
    @MapsId("lifestyleId")
    @JoinColumn(name = "lifestyle_id")
    private LkpLifestyle lifestyle;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EncounterLifestyleId implements Serializable {
        private Long subjectId;
        private Long lifestyleId;
    }
}