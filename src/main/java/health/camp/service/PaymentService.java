package health.camp.service;

import health.camp.dto.payment.PaymentResponse;
import health.camp.model.Payment;
import health.camp.repository.PaymentRepository;
import health.camp.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Page<PaymentResponse> list(String campId, String patientId, String prescriptionId, Pageable pageable) {
        Page<Payment> page;
        if (campId != null && !campId.isBlank()) {
            page = paymentRepository.findByCampId(1L, pageable);
        } else if (patientId != null && !patientId.isBlank()) {
            page = paymentRepository.findByPatientId(patientId, pageable);
        } else if (prescriptionId != null && !prescriptionId.isBlank()) {
            page = paymentRepository.findByPrescriptionId(prescriptionId, pageable);
        } else {
            page = paymentRepository.findAll(pageable);
        }
        return page.map(PaymentService::toResponse);
    }

    public static PaymentResponse toResponse(Payment p) {
        PaymentResponse dto = new PaymentResponse();
        dto.setId(p.getId());
        dto.setPrescriptionId(p.getPrescriptionId());
        dto.setPatientId(p.getPatientId());
        dto.setCampId(p.getCampId());
        dto.setTotalAmount(p.getTotalAmount());
        dto.setPaidAmount(p.getPaidAmount());
        dto.setPendingAmount(p.getPendingAmount());
        dto.setDiscountId(p.getDiscountId());
        dto.setDiscountAmount(p.getDiscountAmount());
        dto.setStatus(p.getStatus());
        dto.setCreatedAt(p.getCreatedAt());
        return dto;
    }
}
