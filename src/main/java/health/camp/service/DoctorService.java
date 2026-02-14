package health.camp.service;

import health.camp.dto.doctor.DoctorRequest;
import health.camp.dto.doctor.DoctorResponse;
import health.camp.model.Doctor;
import health.camp.repository.DoctorRepository;
import health.camp.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final CountersService countersService;


    public List<DoctorResponse> list(String campId, String search, Boolean active) {
        List<Doctor> page;
        if (campId != null && !campId.isBlank()) {
            if (search != null && !search.isBlank()) {
                page = doctorRepository.findByCampIdAndSearch(campId, search);
            } else {
                page = Collections.singletonList(doctorRepository.findByCampId(campId));
            }
        } else if (Boolean.FALSE.equals(active)) {
            page = doctorRepository.findByActive(false);
        } else if (search != null && !search.isBlank()) {
            page = doctorRepository.findBySearch(search);
        } else {
            page = doctorRepository.findAll();
        }
        return page.stream()
                .map(this::toResponse)
                .toList();
    }

    public List<DoctorResponse> listByCampId(String campId) {
        return doctorRepository.findByCampIdsContaining(campId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public DoctorResponse getById(Long id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Doctor", String.valueOf(id)));
        return toResponse(doctor);
    }

    public DoctorResponse create(DoctorRequest request) {
        Doctor doctor = new Doctor();
        doctor.setId(countersService.getNextSequence("doctor_seq"));
        mapRequestToDoctor(request, doctor);
        doctor = doctorRepository.save(doctor);
        return toResponse(doctor);
    }

    public DoctorResponse update(Long id, DoctorRequest request) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Doctor", String.valueOf(id)));
        mapRequestToDoctor(request, doctor);
        if (request.getActive() != null) {
            doctor.setActive(request.getActive());
        }
        doctor = doctorRepository.save(doctor);
        return toResponse(doctor);
    }

    private void mapRequestToDoctor(DoctorRequest request, Doctor doctor) {
        if (request.getName() != null) doctor.setName(request.getName());
        if (request.getSpecialization() != null) doctor.setSpecialization(request.getSpecialization());
        if (request.getPhone() != null) doctor.setPhone(request.getPhone().replaceAll("\\D", ""));
        if (request.getEmail() != null) doctor.setEmail(request.getEmail());
        if (request.getPhotoUrl() != null) doctor.setPhotoUrl(request.getPhotoUrl());
        if (request.getCampIds() != null) doctor.setCampIds(request.getCampIds());
        doctor.setActive(true);
    }

    public DoctorResponse toResponse(Doctor doctor) {
        DoctorResponse dto = new DoctorResponse();
        dto.setId(doctor.getId());
        dto.setName(doctor.getName());
        dto.setSpecialization(doctor.getSpecialization());
        dto.setPhone(doctor.getPhone());
        dto.setEmail(doctor.getEmail());
        dto.setAvatar(doctor.getAvatar());
        dto.setPhotoUrl(doctor.getPhotoUrl());
        dto.setActive(doctor.isActive());
        dto.setCampIds(doctor.getCampIds());
        return dto;
    }
}
