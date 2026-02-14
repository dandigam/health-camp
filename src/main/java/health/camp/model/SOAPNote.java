package health.camp.model;

import health.camp.model.enums.SOAPNoteStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Objects;

@Document(collection = "soap_notes")
public class SOAPNote {

    @Id
    private String id;
    @Indexed
    private String patientId;
    @Indexed
    private String campId;
    private String createdBy;
    private String subjective;
    private SOAPObjective objective;
    private String assessment;
    private String plan;
    @Indexed
    private SOAPNoteStatus status;
    private Instant createdAt;

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

    public SOAPObjective getObjective() {
        return objective;
    }

    public void setObjective(SOAPObjective objective) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SOAPNote soapNote = (SOAPNote) o;
        return Objects.equals(id, soapNote.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
