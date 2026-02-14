package health.camp.dto.patient;

import lombok.Data;

import java.util.List;
@Data
public class PatientOriginalHistory {

    private List<String> conditionsList;
    private String previousHospital;
    private String previousCurrentMedications;
    private String surgeryIllness;

}
