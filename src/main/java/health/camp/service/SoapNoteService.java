package health.camp.service;

import health.camp.dto.soap.SOAPNoteRequest;
import health.camp.dto.soap.SOAPNoteResponse;
import health.camp.dto.soap.SOAPObjectiveDto;
import health.camp.model.SOAPNote;
import health.camp.model.SOAPObjective;
import health.camp.model.enums.SOAPNoteStatus;
import health.camp.repository.PatientRepository;
import health.camp.repository.SOAPNoteRepository;
import health.camp.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class SoapNoteService {

    private final SOAPNoteRepository soapNoteRepository;
    private final PatientRepository patientRepository;

    public SoapNoteService(SOAPNoteRepository soapNoteRepository, PatientRepository patientRepository) {
        this.soapNoteRepository = soapNoteRepository;
        this.patientRepository = patientRepository;
    }

    public Page<SOAPNoteResponse> list(String campId, String patientId, String status, String search, Pageable pageable) {
        Page<SOAPNote> page;
        if (campId != null && !campId.isBlank()) {
            if (status != null && !status.isBlank()) {
                page = soapNoteRepository.findByCampIdAndStatus(campId, SOAPNoteStatus.valueOf(status), pageable);
            } else if (patientId != null && !patientId.isBlank()) {
                page = soapNoteRepository.findByCampIdAndPatientId(campId, patientId, pageable);
            } else {
                page = soapNoteRepository.findByCampId(campId, pageable);
            }
        } else if (patientId != null && !patientId.isBlank()) {
            page = soapNoteRepository.findByPatientId(patientId, pageable);
        } else {
            page = soapNoteRepository.findAll(pageable);
        }
        return page.map(SoapNoteService::toResponse);
    }

    public SOAPNoteResponse getById(String id) {
        SOAPNote note = soapNoteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("SOAPNote", id));
        return toResponse(note);
    }

    public SOAPNoteResponse create(SOAPNoteRequest request, String createdBy) {
        patientRepository.findById(request.getPatientId()).orElseThrow(() -> new ResourceNotFoundException("Patient", request.getPatientId()));
        SOAPNote note = new SOAPNote();
        note.setId(UUID.randomUUID().toString());
        note.setPatientId(request.getPatientId());
        note.setCampId(request.getCampId());
        note.setCreatedBy(createdBy);
        note.setSubjective(request.getSubjective());
        note.setObjective(toObjective(request.getObjective()));
        note.setAssessment(request.getAssessment());
        note.setPlan(request.getPlan());
        note.setStatus(request.getStatus() != null ? request.getStatus() : SOAPNoteStatus.pending);
        note.setCreatedAt(Instant.now());
        note = soapNoteRepository.save(note);
        return toResponse(note);
    }

    public SOAPNoteResponse update(String id, SOAPNoteRequest request) {
        SOAPNote note = soapNoteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("SOAPNote", id));
        if (request.getSubjective() != null) note.setSubjective(request.getSubjective());
        if (request.getObjective() != null) note.setObjective(toObjective(request.getObjective()));
        if (request.getAssessment() != null) note.setAssessment(request.getAssessment());
        if (request.getPlan() != null) note.setPlan(request.getPlan());
        if (request.getStatus() != null) note.setStatus(request.getStatus());
        note = soapNoteRepository.save(note);
        return toResponse(note);
    }

    private SOAPObjective toObjective(SOAPObjectiveDto dto) {
        if (dto == null) return null;
        SOAPObjective o = new SOAPObjective();
        o.setWeight(dto.getWeight());
        o.setBp(dto.getBp());
        o.setPulse(dto.getPulse());
        o.setTemp(dto.getTemp());
        o.setSpo2(dto.getSpo2());
        o.setNotes(dto.getNotes());
        return o;
    }

    public static SOAPNoteResponse toResponse(SOAPNote note) {
        SOAPNoteResponse dto = new SOAPNoteResponse();
        dto.setId(note.getId());
        dto.setPatientId(note.getPatientId());
        dto.setCampId(note.getCampId());
        dto.setCreatedBy(note.getCreatedBy());
        dto.setSubjective(note.getSubjective());
        dto.setObjective(toObjectiveDto(note.getObjective()));
        dto.setAssessment(note.getAssessment());
        dto.setPlan(note.getPlan());
        dto.setStatus(note.getStatus());
        dto.setCreatedAt(note.getCreatedAt());
        return dto;
    }

    private static SOAPObjectiveDto toObjectiveDto(SOAPObjective o) {
        if (o == null) return null;
        SOAPObjectiveDto dto = new SOAPObjectiveDto();
        dto.setWeight(o.getWeight());
        dto.setBp(o.getBp());
        dto.setPulse(o.getPulse());
        dto.setTemp(o.getTemp());
        dto.setSpo2(o.getSpo2());
        dto.setNotes(o.getNotes());
        return dto;
    }
}
