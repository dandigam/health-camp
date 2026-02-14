package health.camp.dto.consultation;

import health.camp.model.enums.ConsultationStatus;

import java.time.Instant;
import java.util.List;

public class ConsultationResponse {

    private String id;
    private String patientId;
    private String doctorId;
    private String campId;
    private String soapNoteId;
    private String chiefComplaint;
    private String medicalHistory;
    private List<String> diagnosis;
    private List<String> labTests;
    private List<String> suggestedOperations;
    private String notes;
    private String prescriptionId;
    private ConsultationStatus status;
    private Instant createdAt;
    //private PrescriptionResponse prescription;

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
        this.diagnosis = diagnosis;
    }

    public List<String> getLabTests() {
        return labTests;
    }

    public void setLabTests(List<String> labTests) {
        this.labTests = labTests;
    }

    public List<String> getSuggestedOperations() {
        return suggestedOperations;
    }

    public void setSuggestedOperations(List<String> suggestedOperations) {
        this.suggestedOperations = suggestedOperations;
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

    }
