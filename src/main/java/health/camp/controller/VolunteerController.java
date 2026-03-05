package health.camp.controller;

import health.camp.dto.VolunteerDto;
import health.camp.entity.Volunteer;
import health.camp.service.VolunteerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/volunteers")
@RequiredArgsConstructor
@CrossOrigin("*")  // Allow CORS for all origins (adjust as needed)
public class VolunteerController {
    private final VolunteerService volunteerService;

    @PostMapping
    public ResponseEntity<VolunteerDto> addVolunteer(@RequestBody VolunteerDto volunteerDto) {
        Volunteer volunteer = toEntity(volunteerDto);
        Volunteer saved = volunteerService.saveVolunteer(volunteer);
        return ResponseEntity.ok(toDto(saved));
    }

    @GetMapping
    public ResponseEntity<List<VolunteerDto>> getAllVolunteers() {
        List<Volunteer> volunteers = volunteerService.getAllVolunteers();
        List<VolunteerDto> dtos = volunteers.stream().map(this::toDto).toList();
        return ResponseEntity.ok(dtos);
    }

    private VolunteerDto toDto(Volunteer volunteer) {
        return new VolunteerDto(
            volunteer.getId(),
            volunteer.getName(),
            volunteer.getPhoneNumber(),
            volunteer.getEmail()
        );
    }

    private Volunteer toEntity(VolunteerDto dto) {
        return Volunteer.builder()
            .id(dto.getId())
            .name(dto.getName())
            .phoneNumber(dto.getPhoneNumber())
            .email(dto.getEmail())
            .build();
    }
}
