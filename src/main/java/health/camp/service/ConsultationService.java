package health.camp.service;

import health.camp.dto.consultation.ConsultationRequest;
import health.camp.dto.consultation.ConsultationResponse;
import health.camp.dto.consultation.PrescriptionItemDto;
import health.camp.model.Consultation;
import health.camp.model.Medicine;
import health.camp.model.Prescription;
import health.camp.model.PrescriptionItem;
import health.camp.model.enums.ConsultationStatus;
import health.camp.model.enums.PrescriptionStatus;
import health.camp.repository.ConsultationRepository;
import health.camp.repository.MedicineRepository;
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
public class ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final MedicineRepository medicineRepository;

    public ConsultationService(ConsultationRepository consultationRepository,
                               PrescriptionRepository prescriptionRepository,
                               MedicineRepository medicineRepository) {
        this.consultationRepository = consultationRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.medicineRepository = medicineRepository;
    }

    public Page<ConsultationResponse> list(String campId, String patientId, String doctorId, String status, Pageable pageable) {
        Page<Consultation> page;
        if (campId != null && !campId.isBlank()) {
            if (status != null && !status.isBlank()) {
                page = consultationRepository.findByCampIdAndStatus(campId, ConsultationStatus.valueOf(status), pageable);
            } else {
                page = consultationRepository.findByCampId(campId, pageable);
            }
        } else if (patientId != null && !patientId.isBlank()) {
            page = consultationRepository.findByPatientId(patientId, pageable);
        } else if (doctorId != null && !doctorId.isBlank()) {
            page = consultationRepository.findByDoctorId(doctorId, pageable);
        } else {
            page = consultationRepository.findAll(pageable);
        }
        //return page.map(this::toResponse);

        return null;
    }

    public ConsultationResponse getById(String id) {
        Consultation c = consultationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Consultation", id));
        ConsultationResponse resp = toResponse(c);
        if (c.getPrescriptionId() != null) {
          //  prescriptionRepository.findById(c.getPrescriptionId()).ifPresent(p ->
                //    resp.setPrescription(PrescriptionService.toResponse(p)));
        }
        return resp;
    }

    public ConsultationResponse create(ConsultationRequest request) {
        Consultation c = new Consultation();
        c.setId(UUID.randomUUID().toString());
        c.setPatientId(request.getPatientId());
        c.setDoctorId(request.getDoctorId());
        c.setCampId(request.getCampId());
        c.setSoapNoteId(request.getSoapNoteId());
        c.setChiefComplaint(request.getChiefComplaint());
        c.setMedicalHistory(request.getMedicalHistory());
        c.setDiagnosis(request.getDiagnosis());
        c.setLabTests(request.getLabTests());
        c.setSuggestedOperations(request.getSuggestedOperations());
        c.setNotes(request.getNotes());
        c.setStatus(request.getStatus() != null ? request.getStatus() : ConsultationStatus.in_progress);
        c.setCreatedAt(Instant.now());
        if (request.getPrescription() != null && request.getPrescription().getItems() != null && !request.getPrescription().getItems().isEmpty()) {
            Prescription prescription = createPrescriptionFromRequest(request, c);
            c.setPrescriptionId(prescription.getId());
            c.setStatus(ConsultationStatus.completed);
        }
        c = consultationRepository.save(c);
        return getById(c.getId());
    }

    public ConsultationResponse update(String id, ConsultationRequest request) {
        Consultation c = consultationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Consultation", id));
        if (request.getChiefComplaint() != null) c.setChiefComplaint(request.getChiefComplaint());
        if (request.getMedicalHistory() != null) c.setMedicalHistory(request.getMedicalHistory());
        if (request.getDiagnosis() != null) c.setDiagnosis(request.getDiagnosis());
        if (request.getLabTests() != null) c.setLabTests(request.getLabTests());
        if (request.getSuggestedOperations() != null) c.setSuggestedOperations(request.getSuggestedOperations());
        if (request.getNotes() != null) c.setNotes(request.getNotes());
        if (request.getStatus() != null) c.setStatus(request.getStatus());
        if (request.getPrescription() != null && request.getPrescription().getItems() != null && !request.getPrescription().getItems().isEmpty()) {
            Prescription prescription;
            if (c.getPrescriptionId() != null) {
                prescription = prescriptionRepository.findById(c.getPrescriptionId()).orElseThrow();
                prescription.setItems(toPrescriptionItems(request.getPrescription().getItems()));
            } else {
                prescription = createPrescriptionFromRequest(request, c);
                c.setPrescriptionId(prescription.getId());
            }
            prescriptionRepository.save(prescription);
            c.setStatus(ConsultationStatus.completed);
        }
        c = consultationRepository.save(c);
        return getById(c.getId());
    }

    private Prescription createPrescriptionFromRequest(ConsultationRequest request, Consultation c) {
        Prescription p = new Prescription();
        p.setId(UUID.randomUUID().toString());
        p.setConsultationId(c.getId());
        p.setPatientId(c.getPatientId());
        p.setDoctorId(c.getDoctorId());
        p.setCampId(c.getCampId());
        p.setItems(toPrescriptionItems(request.getPrescription().getItems()));
        p.setStatus(PrescriptionStatus.pending);
        p.setCreatedAt(Instant.now());
        return prescriptionRepository.save(p);
    }

    private List<PrescriptionItem> toPrescriptionItems(List<PrescriptionItemDto> items) {
        List<PrescriptionItem> list = new ArrayList<>();
        for (PrescriptionItemDto dto : items) {
            Medicine med = medicineRepository.findById(dto.getMedicineId()).orElse(null);
            PrescriptionItem item = new PrescriptionItem();
            item.setMedicineId(dto.getMedicineId());
            item.setMedicineName(med != null ? med.getName() : dto.getMedicineName());
            item.setQuantity(dto.getQuantity());
            item.setMorning(dto.getMorning());
            item.setAfternoon(dto.getAfternoon());
            item.setNight(dto.getNight());
            item.setDays(dto.getDays());
            item.setNotes(dto.getNotes());
            list.add(item);
        }
        return list;
    }

    public static ConsultationResponse toResponse(Consultation c) {
        ConsultationResponse dto = new ConsultationResponse();
        dto.setId(c.getId());
        dto.setPatientId(c.getPatientId());
        dto.setDoctorId(c.getDoctorId());
        dto.setCampId(c.getCampId());
        dto.setSoapNoteId(c.getSoapNoteId());
        dto.setChiefComplaint(c.getChiefComplaint());
        dto.setMedicalHistory(c.getMedicalHistory());
        dto.setDiagnosis(c.getDiagnosis());
        dto.setLabTests(c.getLabTests());
        dto.setSuggestedOperations(c.getSuggestedOperations());
        dto.setNotes(c.getNotes());
        dto.setPrescriptionId(c.getPrescriptionId());
        dto.setStatus(c.getStatus());
        dto.setCreatedAt(c.getCreatedAt());
        return dto;
    }
}
