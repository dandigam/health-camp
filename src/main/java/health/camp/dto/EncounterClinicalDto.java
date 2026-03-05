package health.camp.dto;

import lombok.Data;

@Data
public class EncounterClinicalDto {

    private Long encounterId;

    private EncounterSubjectDto subject;

    //private EncounterVitalsDto vitals;

    //private EncounterAssessmentDto assessment;

    //private EncounterPlanDto plan;
}