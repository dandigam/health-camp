package health.camp.controller;

import health.camp.dto.DoctorDto;
import health.camp.entity.Doctor;
import health.camp.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
@CrossOrigin("*")  // Allow CORS for all origins (adjust as needed)
public class DoctorController {
    
    private final DoctorService doctorService;

    @PostMapping
    public ResponseEntity<DoctorDto> addDoctor(@RequestBody DoctorDto doctorDto) {
        Doctor doctor = toEntity(doctorDto);
        Doctor saved = doctorService.saveDoctor(doctor);
        return ResponseEntity.ok(toDto(saved));
    }

    @GetMapping
    public ResponseEntity<List<DoctorDto>> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        List<DoctorDto> dtos = doctors.stream().map(this::toDto).toList();
        return ResponseEntity.ok(dtos);
    }

    private DoctorDto toDto(Doctor doctor) {
        return new DoctorDto(
            doctor.getId(),
            doctor.getName(),
            doctor.getSpecialization(),
            doctor.getPhoneNumber(),
            doctor.getEmail()
        );
    }

    private Doctor toEntity(DoctorDto dto) {
        return Doctor.builder()
            .id(dto.getId())
            .name(dto.getName())
            .specialization(dto.getSpecialization())
            .phoneNumber(dto.getPhoneNumber())
            .email(dto.getEmail())
            .build();
    }
}
