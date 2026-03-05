package health.camp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "encounter_conditions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EncounterCondition {

    @EmbeddedId
    private EncounterConditionId id = new EncounterConditionId();

    @ManyToOne
    @MapsId("subjectId")
    @JoinColumn(name = "subject_id")
    private EncounterSubject encounterSubject;

    @ManyToOne
    @MapsId("conditionId")
    @JoinColumn(name = "condition_id")
    private LkpCondition condition;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EncounterConditionId implements Serializable {
        private Long subjectId;
        private Long conditionId;
    }
}