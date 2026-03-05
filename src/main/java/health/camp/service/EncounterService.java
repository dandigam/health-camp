package health.camp.service;

import health.camp.dto.EncounterClinicalDto;
import health.camp.entity.Encounter;
import health.camp.repository.EncounterRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import health.camp.dto.DoctorDto;
import health.camp.dto.EncounterDto;
import health.camp.dto.PatientSummaryDto;
import health.camp.repository.EncounterSubjectRepository;

@Service
@RequiredArgsConstructor
public class EncounterService {
    private final EncounterRepository encounterRepository;
    private final EncounterSubjectRepository encounterSubjectRepository;
    private final EncounterSubjectService encounterSubjectService;

    public Encounter saveEncounter(Encounter encounter) {
        return encounterRepository.save(encounter);
    }

    @Transactional
    public void saveClinicalData(EncounterClinicalDto dto){

        if(dto.getSubject()!=null){
            encounterSubjectService.saveOrUpdate(dto.getSubject());
        }
/*

        if(dto.getVitals()!=null){
            vitalsService.saveOrUpdate(dto.getVitals());
        }

        if(dto.getAssessment()!=null){
            assessmentService.saveOrUpdate(dto.getAssessment());
        }

        if(dto.getPlan()!=null){
            planService.saveOrUpdate(dto.getPlan());
        }
*/

    }

    public Optional<Encounter> getEncounterById(Long id) {
        return encounterRepository.findById(id);
    }

    public void deleteEncounter(Long id) {
        encounterRepository.deleteById(id);
    }

    public EncounterDto toDto(Encounter encounter) {
        EncounterDto dto = new EncounterDto();
        dto.setId(encounter.getId());
        dto.setPatientId(encounter.getPatient() != null ? encounter.getPatient().getId() : null);
        dto.setCampEventId(encounter.getCampEvent() != null ? encounter.getCampEvent().getId() : null);
        dto.setDoctorId(encounter.getDoctor() != null ? encounter.getDoctor().getId() : null);
        dto.setStatus(encounter.getStatus());
        dto.setTokenNumber(encounter.getTokenNumber());
        dto.setCreatedAt(encounter.getCreatedAt());
        // Set subjectId if PatientSubjectAssessment exists for this encounter
        encounterSubjectRepository.findByEncounterId(encounter.getId())
                .ifPresent(assessment -> dto.setSubjectId(assessment.getId()));
        return dto;
    }

    public Encounter toEntity(EncounterDto dto) {
        Encounter encounter = new Encounter();
        encounter.setId(dto.getId());
        // Set patient, camp, doctor as needed (only by id here)
        if (dto.getPatientId() != null) {
            var patient = new health.camp.entity.Patient();
            patient.setId(dto.getPatientId());
            encounter.setPatient(patient);
        }
        if (dto.getCampEventId() != null) {
            var camp = new health.camp.entity.CampEvent();
            camp.setId(dto.getCampEventId());
            encounter.setCampEvent(camp);
        }
        if (dto.getDoctorId() != null) {
            var doctor = new health.camp.entity.Doctor();
            doctor.setId(dto.getDoctorId());
            encounter.setDoctor(doctor);
        }
        encounter.setStatus(dto.getStatus());
        encounter.setTokenNumber(dto.getTokenNumber());
        encounter.setCreatedAt(dto.getCreatedAt());
        return encounter;
    }

    public List<Encounter> getEncountersByCampEventId(Long campEventId) {
        return encounterRepository.findByCampEventId(campEventId);
    }

    public List<PatientSummaryDto> getPatientSummariesByCampEventId(Long campEventId) {
        List<Encounter> encounters = getEncountersByCampEventId(campEventId);
        return encounters.stream().map(encounter -> {
            var patient = encounter.getPatient();
            PatientSummaryDto dto = new PatientSummaryDto();
            dto.setPatientname(patient.getFirstName().concat(" " +patient.getLastName()));
            dto.setGender(patient != null ? patient.getGender().name() : null);
            dto.setAge(patient != null ? patient.getAge() : null);
            dto.setMr(patient != null ? patient.getPatientId() : null);
            dto.setEncounter(new PatientSummaryDto.EncounterInfo(
                encounter.getId(),
                encounter.getStatus()
            ));
            // Map doctor to DoctorDto
            DoctorDto doctorDto = null;
            var doctor = encounter.getDoctor();
            if (doctor != null) {
                doctorDto = new DoctorDto();
                doctorDto.setId(doctor.getId());
                doctorDto.setName(doctor.getName());
                doctorDto.setSpecialization(doctor.getSpecialization());
                doctorDto.setPhoneNumber(doctor.getPhoneNumber());
                doctorDto.setEmail(doctor.getEmail());
            }
            dto.setDoctor(doctorDto);
            return dto;
        }).toList();
    }
}
