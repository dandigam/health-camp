package health.camp.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class EncounterSubjectDto {
    private Long id;
    private Long encounterId;
    private String chiefComplaintText;
    private String additionalNotes;
    private LocalDateTime createdAt;

    // For response: names
    private Set<String> symptoms;
    private Set<String> conditions;
    private Set<String> lifestyles;

    // For request (save/update): IDs
    private Set<Long> symptomIds;
    private Set<Long> conditionIds;
    private Set<Long> lifestyleIds;
}
