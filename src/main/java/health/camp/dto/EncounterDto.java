package health.camp.dto;

import health.camp.model.enums.EncounterStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EncounterDto {
    private Long id;
    private Long patientId;
    private Long campEventId;
    private Long doctorId;
    private Long subjectId;
    private EncounterStatus status;
    private Integer tokenNumber;
    private LocalDateTime createdAt;

    private EncounterSubjectDto encounterSubject;
}
