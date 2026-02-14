package health.camp.dto.soap;

import health.camp.model.enums.SOAPNoteStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SOAPNoteRequest {

    @NotBlank(message = "patientId is required")
    private String patientId;

    @NotBlank(message = "campId is required")
    private String campId;

    @Size(max = 10000)
    private String subjective;

    private SOAPObjectiveDto objective;

    @Size(max = 5000)
    private String assessment;

    @Size(max = 5000)
    private String plan;

    private SOAPNoteStatus status = SOAPNoteStatus.pending;

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getCampId() {
        return campId;
    }

    public void setCampId(String campId) {
        this.campId = campId;
    }

    public String getSubjective() {
        return subjective;
    }

    public void setSubjective(String subjective) {
        this.subjective = subjective;
    }

    public SOAPObjectiveDto getObjective() {
        return objective;
    }

    public void setObjective(SOAPObjectiveDto objective) {
        this.objective = objective;
    }

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public SOAPNoteStatus getStatus() {
        return status;
    }

    public void setStatus(SOAPNoteStatus status) {
        this.status = status;
    }
}
