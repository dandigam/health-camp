package health.camp.dto.prescription;

import health.camp.dto.payment.PaymentResponse;

public class DispenseResponse {

    private PrescriptionResponse prescription;
    private PaymentResponse payment;

    public PrescriptionResponse getPrescription() {
        return prescription;
    }

    public void setPrescription(PrescriptionResponse prescription) {
        this.prescription = prescription;
    }

    public PaymentResponse getPayment() {
        return payment;
    }

    public void setPayment(PaymentResponse payment) {
        this.payment = payment;
    }
}
