package health.camp.dto.soap;

import health.camp.model.enums.SOAPNoteStatus;

import java.time.Instant;

public class SOAPNoteResponse {

    private String id;
    private String patientId;
    private String campId;
    private String createdBy;
    private String subjective;
    private SOAPObjectiveDto objective;
    private String assessment;
    private String plan;
    private SOAPNoteStatus status;
    private Instant createdAt;
    private String patientName;
    private String patientPatientId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientPatientId() {
        return patientPatientId;
    }

    public void setPatientPatientId(String patientPatientId) {
        this.patientPatientId = patientPatientId;
    }
}
