package health.camp.model;

import health.camp.model.enums.ConsultationStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collection = "consultations")
public class Consultation {

    @Id
    private String id;
    @Indexed
    private String patientId;
    @Indexed
    private String doctorId;
    @Indexed
    private String campId;
    @Indexed
    private String soapNoteId;
    private String chiefComplaint;
    private String medicalHistory;
    private List<String> diagnosis = new ArrayList<>();
    private List<String> labTests = new ArrayList<>();
    private List<String> suggestedOperations = new ArrayList<>();
    private String notes;
    private String prescriptionId;
    @Indexed
    private ConsultationStatus status;
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

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getCampId() {
        return campId;
    }

    public void setCampId(String campId) {
        this.campId = campId;
    }

    public String getSoapNoteId() {
        return soapNoteId;
    }

    public void setSoapNoteId(String soapNoteId) {
        this.soapNoteId = soapNoteId;
    }

    public String getChiefComplaint() {
        return chiefComplaint;
    }

    public void setChiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public List<String> getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(List<String> diagnosis) {
        this.diagnosis = diagnosis != null ? diagnosis : new ArrayList<>();
    }

    public List<String> getLabTests() {
        return labTests;
    }

    public void setLabTests(List<String> labTests) {
        this.labTests = labTests != null ? labTests : new ArrayList<>();
    }

    public List<String> getSuggestedOperations() {
        return suggestedOperations;
    }

    public void setSuggestedOperations(List<String> suggestedOperations) {
        this.suggestedOperations = suggestedOperations != null ? suggestedOperations : new ArrayList<>();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public ConsultationStatus getStatus() {
        return status;
    }

    public void setStatus(ConsultationStatus status) {
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
        Consultation that = (Consultation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
