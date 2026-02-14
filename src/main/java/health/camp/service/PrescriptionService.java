package health.camp.service;

import health.camp.dto.prescription.DispenseRequest;
import health.camp.dto.prescription.DispenseResponse;
import health.camp.dto.prescription.PrescriptionResponse;
import health.camp.dto.payment.PaymentResponse;
import health.camp.model.Payment;
import health.camp.model.Prescription;
import health.camp.model.PrescriptionItem;
import health.camp.model.enums.PaymentStatus;
import health.camp.model.enums.PrescriptionStatus;
import health.camp.repository.PaymentRepository;
import health.camp.repository.PrescriptionRepository;
import health.camp.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final PaymentRepository paymentRepository;

    public PrescriptionService(PrescriptionRepository prescriptionRepository, PaymentRepository paymentRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.paymentRepository = paymentRepository;
    }

    public Page<PrescriptionResponse> list(String campId, String patientId, String status, Pageable pageable) {
        Page<Prescription> page;
        if (campId != null && !campId.isBlank()) {
            if (status != null && !status.isBlank()) {
                page = prescriptionRepository.findByCampIdAndStatus(campId, health.camp.model.enums.PrescriptionStatus.valueOf(status), pageable);
            } else {
                page = prescriptionRepository.findByCampId(campId, pageable);
            }
        } else if (patientId != null && !patientId.isBlank()) {
            page = prescriptionRepository.findByPatientId(patientId, pageable);
        } else {
            page = prescriptionRepository.findAll(pageable);
        }
        return page.map(PrescriptionService::toResponse);
    }

    public PrescriptionResponse getById(String id) {
        Prescription p = prescriptionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Prescription", id));
        return toResponse(p);
    }

    public DispenseResponse dispense(String id, DispenseRequest request) {
        Prescription p = prescriptionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Prescription", id));
        Payment payment = new Payment();
        payment.setId(UUID.randomUUID().toString());
        payment.setPrescriptionId(id);
        payment.setPatientId(p.getPatientId());
        payment.setCampId(p.getCampId());
        payment.setTotalAmount(request.getPayment().getTotalAmount());
        payment.setPaidAmount(request.getPayment().getPaidAmount());
        payment.setPendingAmount(request.getPayment().getPendingAmount());
        payment.setStatus(request.getPayment().getStatus() != null ? PaymentStatus.valueOf(request.getPayment().getStatus()) : PaymentStatus.full);
        payment.setCreatedAt(Instant.now());
        payment = paymentRepository.save(payment);
        p.setStatus(PrescriptionStatus.dispensed);
        p = prescriptionRepository.save(p);
        DispenseResponse response = new DispenseResponse();
        response.setPrescription(toResponse(p));
        response.setPayment(PaymentService.toResponse(payment));
        return response;
    }

    public static PrescriptionResponse toResponse(Prescription p) {
        PrescriptionResponse dto = new PrescriptionResponse();
        dto.setId(p.getId());
        dto.setConsultationId(p.getConsultationId());
        dto.setPatientId(p.getPatientId());
        dto.setDoctorId(p.getDoctorId());
        dto.setCampId(p.getCampId());
        dto.setItems(toItemResponses(p.getItems()));
        dto.setStatus(p.getStatus());
        dto.setCreatedAt(p.getCreatedAt());
        return dto;
    }

    private static List<PrescriptionResponse.PrescriptionItemResponse> toItemResponses(List<PrescriptionItem> items) {
        if (items == null) return new ArrayList<>();
        List<PrescriptionResponse.PrescriptionItemResponse> list = new ArrayList<>();
        for (PrescriptionItem item : items) {
            PrescriptionResponse.PrescriptionItemResponse ir = new PrescriptionResponse.PrescriptionItemResponse();
            ir.setMedicineId(item.getMedicineId());
            ir.setMedicineName(item.getMedicineName());
            ir.setQuantity(item.getQuantity());
            ir.setMorning(item.getMorning());
            ir.setAfternoon(item.getAfternoon());
            ir.setNight(item.getNight());
            ir.setDays(item.getDays());
            ir.setNotes(item.getNotes());
            list.add(ir);
        }
        return list;
    }
}
