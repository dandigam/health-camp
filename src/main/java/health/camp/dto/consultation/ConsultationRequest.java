package health.camp.dto.consultation;

import health.camp.model.enums.ConsultationStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class ConsultationRequest {

    @NotBlank(message = "patientId is required")
    private String patientId;

    @NotBlank(message = "doctorId is required")
    private String doctorId;

    @NotBlank(message = "campId is required")
    private String campId;

    @NotBlank(message = "soapNoteId is required")
    private String soapNoteId;

    @Size(max = 2000)
    private String chiefComplaint;

    @Size(max = 5000)
    private String medicalHistory;

    private List<String> diagnosis;

    private List<String> labTests;

    private List<String> suggestedOperations;

    @Size(max = 5000)
    private String notes;

    private ConsultationStatus status = ConsultationStatus.in_progress;

    @Valid
    private PrescriptionDto prescription;

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

    public ConsultationStatus getStatus() {
        return status;
    }

    public void setStatus(ConsultationStatus status) {
        this.status = status;
    }

    public PrescriptionDto getPrescription() {
        return prescription;
    }

    public void setPrescription(PrescriptionDto prescription) {
        this.prescription = prescription;
    }

    public static class PrescriptionDto {
        @Valid
        private List<PrescriptionItemDto> items;

        public List<PrescriptionItemDto> getItems() {
            return items;
        }

        public void setItems(List<PrescriptionItemDto> items) {
            this.items = items;
        }
    }
}
