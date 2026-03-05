package health.camp.dto;

import health.camp.entity.Camp;
import health.camp.model.enums.EncounterStatus;
import lombok.Data;

@Data
public class PatientSummaryDto {

    private String patientname;
    private String mr;
    private String gender;
    private Integer age;
    private EncounterInfo encounter;
    private Camp camp;
    private DoctorDto doctor;

    @Data
    public static class EncounterInfo {
        private Long id;
        private EncounterStatus encounterStatus;
        public EncounterInfo(Long id, EncounterStatus status) {
            this.id = id;
            this.encounterStatus = status;
        }
    }
}
