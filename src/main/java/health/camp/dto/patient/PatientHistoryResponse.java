package health.camp.dto.patient;

import health.camp.dto.consultation.ConsultationResponse;
import health.camp.dto.prescription.PrescriptionResponse;
import health.camp.dto.soap.SOAPNoteResponse;
import health.camp.dto.payment.PaymentResponse;

import java.util.ArrayList;
import java.util.List;

public class PatientHistoryResponse {

    private PatientResponse patient;
    private List<SOAPNoteResponse> soapNotes = new ArrayList<>();
    private List<ConsultationResponse> consultations = new ArrayList<>();
    private List<PrescriptionResponse> prescriptions = new ArrayList<>();
    private List<PaymentResponse> payments = new ArrayList<>();

    public PatientResponse getPatient() {
        return patient;
    }

    public void setPatient(PatientResponse patient) {
        this.patient = patient;
    }

    public List<SOAPNoteResponse> getSoapNotes() {
        return soapNotes;
    }

    public void setSoapNotes(List<SOAPNoteResponse> soapNotes) {
        this.soapNotes = soapNotes != null ? soapNotes : new ArrayList<>();
    }

    public List<ConsultationResponse> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<ConsultationResponse> consultations) {
        this.consultations = consultations != null ? consultations : new ArrayList<>();
    }

    public List<PrescriptionResponse> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<PrescriptionResponse> prescriptions) {
        this.prescriptions = prescriptions != null ? prescriptions : new ArrayList<>();
    }

    public List<PaymentResponse> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentResponse> payments) {
        this.payments = payments != null ? payments : new ArrayList<>();
    }
}
