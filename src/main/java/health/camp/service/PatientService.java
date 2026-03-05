package health.camp.service;

import health.camp.dto.patient.PatientRequest;
import health.camp.dto.patient.PatientResponse;
import health.camp.entity.Address;
import health.camp.entity.Encounter;
import health.camp.entity.MedicalConditionLookup;
import health.camp.entity.Patient;
import health.camp.entity.PatientMedicalCondition;
import health.camp.entity.PatientOriginalHistory;
import health.camp.repository.EncounterRepository;
import health.camp.repository.MedicalConditionLookupRepository;
import health.camp.repository.PatientRepository;
import health.camp.exception.ResourceNotFoundException;
import health.camp.model.enums.EncounterStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {
    /**
     * Get total patient registrations (for dashboard)
     */
    public long countAllPatients() {
        return patientRepository.count();
    }

    private final PatientRepository patientRepository;
    private final MedicalConditionLookupRepository medicalConditionLookupRepository;
    private final EncounterRepository encounterRepository;

    /**
     * Get all patients with optional search
     */
    public Page<PatientResponse> list(String search, Pageable pageable) {
        Page<Patient> page;
        if (search != null && !search.isBlank()) {
            page = patientRepository.searchPatients(search, pageable);
        } else {
            page = patientRepository.findAll(pageable);
        }
        return page.map(this::toResponse);
    }

    /**
     * Get patient by ID
     */
    public PatientResponse getById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", id.toString()));
        return toResponse(patient);
    }

    /**
     * Save patient - creates if no ID, updates if ID exists
     */
    @Transactional
    public PatientResponse save(PatientRequest request) {
        Patient patient;

        if (request.getId() != null) {
            // Update existing
            patient = patientRepository.findById(request.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Patient", request.getId().toString()));
        } else {
            // Create new
            patient = new Patient();
            patient.setPatientId(generatePatientId());
        }

        // Update basic fields
        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setFatherSpouseName(request.getFatherSpouseName());
        patient.setGender(request.getGender());
        patient.setAge(request.getAge());
        patient.setMaritalStatus(request.getMaritalStatus());
        patient.setPhoneNumber(request.getPhoneNumber());
        patient.setPhotoUrl(request.getPhotoUrl());
        patient.setPaymentType(request.getPaymentType());
        patient.setPaymentPercentage(request.getPaymentPercentage());
        patient.setHasMedicalHistory(request.getHasMedicalHistory());

        // Update address
        if (request.getAddress() != null) {
            Address address = patient.getAddress();
            if (address == null) {
                address = new Address();
            }
            address.setStateId(request.getAddress().getStateId());
            address.setState(request.getAddress().getState());
            address.setDistrictId(request.getAddress().getDistrictId());
            address.setDistrict(request.getAddress().getDistrict());
            address.setMandalId(request.getAddress().getMandalId());
            address.setMandal(request.getAddress().getMandal());
            address.setCityVillage(request.getAddress().getCityVillage());
            address.setStreetAddress(request.getAddress().getStreetAddress());
            address.setPinCode(request.getAddress().getPinCode());
            patient.setAddress(address);
        }

        // Update medical history
        if (Boolean.TRUE.equals(request.getHasMedicalHistory()) && request.getMedicalHistory() != null) {
            PatientOriginalHistory history = patient.getMedicalHistory();
            if (history == null) {
                history = new PatientOriginalHistory();
            }
            // Fix field names to match DTO
            history.setPreviousHospitalName(request.getMedicalHistory().getPreviousHospital());
            history.setCurrentMedications(request.getMedicalHistory().getCurrentMedications());
            history.setPastSurgeryMajorIllness(request.getMedicalHistory().getPastSurgery());

            // Handle conditions as list of string IDs
            List<Long> conditionIds = request.getMedicalHistory().getConditions();
            if (conditionIds != null) {
                // Clear existing conditions
                if (history.getConditions() != null) {
                    history.getConditions().clear();
                } else {
                    history.setConditions(new ArrayList<>());
                }

                for (Long condId : conditionIds) {
                    String condIdStr = condId.toString();
                    MedicalConditionLookup lookup = medicalConditionLookupRepository.findById(condId)
                        .orElseThrow(() -> new ResourceNotFoundException("MedicalCondition", condIdStr));

                    PatientMedicalCondition condition = PatientMedicalCondition.builder()
                        .patientHistory(history)
                        .condition(lookup)
                        .build();
                    history.getConditions().add(condition);
                }
            }

            patient.setMedicalHistory(history);
        } else if (Boolean.FALSE.equals(request.getHasMedicalHistory())) {
            patient.setMedicalHistory(null);
        }

        patient = patientRepository.save(patient);


        // Encounter creation
        if (request.isEncounterCreate()) {
            Encounter encounter = new Encounter();
            // Set patient reference
            encounter.setPatient(patient);
            // Set camp event reference if provided in request
            if (request.getCampEventId() != null) {
                var campEvent = new health.camp.entity.CampEvent();
                campEvent.setId(request.getCampEventId());
                encounter.setCampEvent(campEvent);
                
            }
            // Set doctor reference if provided in request
            if (request.getDoctorId() != null) {
                var doctor = new health.camp.entity.Doctor();
                doctor.setId(request.getDoctorId());
                encounter.setDoctor(doctor);
            }
            // Set status (default to WAITING if not provided)
            encounter.setStatus(EncounterStatus.WAITING);
            // Set token number (auto-increment for camp)
          
            // Set createdAt
            encounter.setCreatedAt(java.time.LocalDateTime.now());
            encounterRepository.save(encounter);
        }

        return toResponse(patient);
    }

    /**
     * Get all active medical conditions for lookup
     */
    public List<MedicalConditionLookup> getMedicalConditions() {
        return medicalConditionLookupRepository.findByIsActiveTrueOrderByDisplayOrderAsc();
    }

    private String generatePatientId() {
        String datePart = DateTimeFormatter.ofPattern("yyyyMMdd").format(java.time.LocalDate.now());
        long count = patientRepository.count() + 1;
        return "PAT-" + datePart + "-" + String.format("%05d", count);
    }

    public PatientResponse toResponse(Patient patient) {
        PatientResponse.PatientResponseBuilder builder = PatientResponse.builder()
                .id(patient.getId())
                .patientId(patient.getPatientId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .fatherSpouseName(patient.getFatherSpouseName())
                .gender(patient.getGender())
                .age(patient.getAge())
                .maritalStatus(patient.getMaritalStatus())
                .phoneNumber(patient.getPhoneNumber())
                .photoUrl(patient.getPhotoUrl())
                .paymentType(patient.getPaymentType())
                .paymentPercentage(patient.getPaymentPercentage())
                .hasMedicalHistory(patient.getHasMedicalHistory())
                .createdBy(patient.getCreatedBy())
                .createdAt(patient.getCreatedAt())
                .updatedBy(patient.getUpdatedBy())
                .updatedAt(patient.getUpdatedAt());

        // Map address
        //Encounters
        if (patient.getAddress() != null) {
            Address addr = patient.getAddress();
            builder.address(PatientResponse.AddressDto.builder()
                    .id(addr.getId())
                    .stateId(addr.getStateId())
                    .state(addr.getState())
                    .districtId(addr.getDistrictId())
                    .district(addr.getDistrict())
                    .mandalId(addr.getMandalId())
                    .mandal(addr.getMandal())
                    .cityVillage(addr.getCityVillage())
                    .streetAddress(addr.getStreetAddress())
                    .pinCode(addr.getPinCode())
                    .build());
        }

        // Map medical history
        if (patient.getMedicalHistory() != null) {
            PatientOriginalHistory hist = patient.getMedicalHistory();
            List<Long> conditionIds = new ArrayList<>();
            if (hist.getConditions() != null) {
                conditionIds = hist.getConditions().stream()
                    .map(c -> c.getCondition().getId())
                    .collect(Collectors.toList());
            }
            builder.medicalHistory(PatientResponse.MedicalHistoryDto.builder()
                .conditions(conditionIds)
                .previousHospital(hist.getPreviousHospitalName())
                .currentMedications(hist.getCurrentMedications())
                .pastSurgery(hist.getPastSurgeryMajorIllness())
                .build());
        }

        return builder.build();
    }
}
