package health.camp.service;

import health.camp.dto.patient.PatientHistoryResponse;
import health.camp.dto.patient.PatientRequest;
import health.camp.dto.patient.PatientResponse;
import health.camp.model.Camp;
import health.camp.model.Consultation;
import health.camp.model.Patient;
import health.camp.model.Payment;
import health.camp.model.Prescription;
import health.camp.model.SOAPNote;
import health.camp.repository.CampRepository;
import health.camp.repository.ConsultationRepository;
import health.camp.repository.PatientRepository;
import health.camp.repository.PaymentRepository;
import health.camp.repository.PrescriptionRepository;
import health.camp.repository.SOAPNoteRepository;
import health.camp.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final CountersService countersService;
    private final PatientRepository patientRepository;
    private final CampRepository campRepository;
    private final SOAPNoteRepository soapNoteRepository;
    private final ConsultationRepository consultationRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final PaymentRepository paymentRepository;


    public Page<PatientResponse> list(String campId, String search, Pageable pageable) {
        Page<Patient> page;
        if (campId != null && !campId.isBlank()) {
            if (search != null && !search.isBlank()) {
                page = patientRepository.findByCampIdAndSearch(campId, search, pageable);
            } else {
                page = patientRepository.findByCampId(campId, pageable);
            }
        } else if (search != null && !search.isBlank()) {
            page = patientRepository.findBySearch(search, pageable);
        } else {
            page = patientRepository.findAll(pageable);
        }
        return page.map(this::toResponse);
    }

    public PatientResponse getById(String id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patient", id));
        return toResponse(patient);
    }

    public PatientResponse create(PatientRequest request) {
        //Camp camp = campRepository.findById(1L).orElseThrow(() -> new ResourceNotFoundException("Camp", request.getCampId()));
        //String patientId = generatePatientId(camp);
        Patient patient = new Patient();
        patient.setId(countersService.getNextSequence("patient_seq"));
        patient.setPatientId("TEST");
       // patient.setCampId(request.getCampId());
        patient.setName(request.getName());
        patient.setSurname(request.getSurname());
        patient.setFatherName(request.getFatherName());
        patient.setGender(request.getGender());
        patient.setAge(request.getAge());
        patient.setPhone(request.getPhone());
        patient.setAddress(request.getAddress());
        patient.setVillage(request.getVillage());
        patient.setDistrict(request.getDistrict());
        patient.setState(request.getState());
        patient.setPhotoUrl(request.getPhotoUrl());
        patient.setMaritalStatus(request.getMaritalStatus());
        patient.setCreatedAt(LocalDateTime.now());
        patient = patientRepository.save(patient);
        return toResponse(patient);
    }

    public PatientHistoryResponse getHistory(String id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patient", id));
        PatientHistoryResponse response = new PatientHistoryResponse();
        response.setPatient(toResponse(patient));
        List<SOAPNote> soapNotes = soapNoteRepository.findByPatientId(id, Pageable.unpaged()).getContent();
        response.setSoapNotes(soapNotes.stream().map(health.camp.service.SoapNoteService::toResponse).toList());
        List<Consultation> consultations = consultationRepository.findByPatientId(id, Pageable.unpaged()).getContent();
        response.setConsultations(consultations.stream().map(health.camp.service.ConsultationService::toResponse).toList());
        List<Prescription> prescriptions = prescriptionRepository.findByPatientId(id, Pageable.unpaged()).getContent();
        response.setPrescriptions(prescriptions.stream().map(health.camp.service.PrescriptionService::toResponse).toList());
        List<Payment> payments = paymentRepository.findByPatientId(id, Pageable.unpaged()).getContent();
        response.setPayments(payments.stream().map(health.camp.service.PaymentService::toResponse).toList());
        return response;
    }

    private String generatePatientId(Camp camp) {
        String prefix = camp.getName().replaceAll("[^A-Za-z]", "").toUpperCase();
        if (prefix.length() > 4) prefix = prefix.substring(0, 4);
        if (prefix.isEmpty()) prefix = "CAMP";
        String datePart = DateTimeFormatter.ofPattern("MMMdd").format(java.time.LocalDate.now()).toUpperCase();
        long count = patientRepository.countByCampId(camp.getId()) + 1;
        return prefix + "-" + datePart + "-" + count;
    }

    public PatientResponse toResponse(Patient patient) {
        PatientResponse dto = new PatientResponse();
        dto.setId(patient.getId());
        dto.setPatientId(patient.getPatientId());
        dto.setCampId(patient.getCampId());
        dto.setName(patient.getName());
        dto.setSurname(patient.getSurname());
        dto.setFatherName(patient.getFatherName());
        dto.setAge(patient.getAge());
        dto.setGender(patient.getGender());
        dto.setPhone(patient.getPhone());
        dto.setAddress(patient.getAddress());
        dto.setVillage(patient.getVillage());
        dto.setDistrict(patient.getDistrict());
        dto.setState(patient.getState());
        dto.setPhotoUrl(patient.getPhotoUrl());
        dto.setCreatedAt(patient.getCreatedAt());
        return dto;
    }
}
