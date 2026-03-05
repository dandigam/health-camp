package health.camp.controller;

import health.camp.dto.CampEventDto;
import health.camp.entity.CampEvent;
import health.camp.service.CampEventService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import health.camp.entity.Camp;

@RestController
@RequestMapping("/api/camp-events")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CampEventController {

    private final CampEventService campEventService;

    @PostMapping
    public ResponseEntity<CampEventDto> addOrUpdateEvent(@RequestBody CampEventDto eventDto) {
        Camp camp = null;
        if (eventDto.getCampId() != null) {
            camp = new Camp();
            camp.setId(eventDto.getCampId());
        }
        CampEvent event = CampEvent.builder()
            .id(eventDto.getId())
            .status(eventDto.getStatus())
            .startDate(eventDto.getStartDate())
            .endDate(eventDto.getEndDate())
            .camp(camp)
            .build();

        // Save event to CAMP_EVENTS table
        CampEvent saved = campEventService.saveCampEvent(event);
        eventDto.setId(saved.getId());

        // Save doctor assignments to DOCTOR_CAMP table
        if (eventDto.getDoctorsList() != null) {
            campEventService.assignDoctorsToCamp(camp, eventDto.getDoctorsList());
        }


        // Save staff assignments to VOLUNTEER_CAMP table
        if (eventDto.getStaffList() != null) {
            campEventService.assignStaffToCamp(camp, eventDto.getStaffList());
        }

        return ResponseEntity.ok(eventDto);
    }

    /**
     * Get all camp events as DTOs (for JSON serialization)
     */
    @GetMapping
    public ResponseEntity<List<CampEventDto>> getAllCampEvents() {
        List<CampEvent> events = campEventService.getAllCampEvents();
        List<CampEventDto> dtos = events.stream().map(event -> {
            CampEventDto dto = new CampEventDto();
            dto.setId(event.getId());
            dto.setCampId(event.getCamp() != null ? event.getCamp().getId() : null);
            dto.setStatus(event.getStatus());
            dto.setStartDate(event.getStartDate());
            dto.setEndDate(event.getEndDate());
            // Load camp name
            dto.setCampName(event.getCamp() != null ? event.getCamp().getCampName() : null);
            // Load doctorsList
            if (event.getCamp() != null && event.getCamp().getDoctorCamps() != null) {
                dto.setDoctorsList(event.getCamp().getDoctorCamps().stream()
                    .filter(dc -> dc.getDoctor() != null)
                    .map(dc -> dc.getDoctor().getId())
                    .toList());
            }
            // Load staffList
            if (event.getCamp() != null && event.getCamp().getVolunteerCamps() != null) {
                dto.setStaffList(event.getCamp().getVolunteerCamps().stream()
                    .filter(vc -> vc.getVolunteer() != null)
                    .map(vc -> vc.getVolunteer().getId())
                    .toList());
            }
            dto.setState(event.getCamp() != null ? event.getCamp().getState() : null);
            dto.setDistrict(event.getCamp() != null ? event.getCamp().getDistrict() : null);
            dto.setMandal(event.getCamp() != null ? event.getCamp().getMandal() : null);
            dto.setCity(event.getCamp() != null ? event.getCamp().getCity() : null);
            dto.setAddress(event.getCamp() != null ? event.getCamp().getAddress() : null);
            dto.setPinCode(event.getCamp() != null ? event.getCamp().getPinCode() : null);
            return dto;
        }).toList();
        return ResponseEntity.ok(dtos);
    }

    /**
     * Update the status of a camp event (start/close) by event ID
     */
    @PatchMapping("/{eventId}/status")
    public ResponseEntity<CampEvent> updateEventStatus(@PathVariable Long eventId, @RequestParam String status) {
        CampEvent updated = campEventService.updateEventStatus(eventId, status);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    /**
     * Update a camp event by ID (PUT)
     */
    @PutMapping("/{eventId}")
    public ResponseEntity<CampEventDto> updateCampEvent(@PathVariable Long eventId, @RequestBody CampEventDto eventDto) {
        CampEvent existing = campEventService.getCampEventById(eventId);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        // Update fields
        existing.setStatus(eventDto.getStatus());
        existing.setStartDate(eventDto.getStartDate());
        existing.setEndDate(eventDto.getEndDate());
        // Optionally update camp reference if campId is provided
        if (eventDto.getCampId() != null) {
            Camp camp = new Camp();
            camp.setId(eventDto.getCampId());
            existing.setCamp(camp);
        }
        CampEvent saved = campEventService.saveCampEvent(existing);
        // Update doctor and staff assignments
        if (eventDto.getDoctorsList() != null) {
            campEventService.assignDoctorsToCamp(saved.getCamp(), eventDto.getDoctorsList());
        }
        if (eventDto.getStaffList() != null) {
            campEventService.assignStaffToCamp(saved.getCamp(), eventDto.getStaffList());
        }
        // Prepare response DTO
        CampEventDto dto = new CampEventDto();
        dto.setId(saved.getId());
        dto.setCampId(saved.getCamp() != null ? saved.getCamp().getId() : null);
        dto.setStatus(saved.getStatus());
        dto.setStartDate(saved.getStartDate());
        dto.setEndDate(saved.getEndDate());
        dto.setCampName(saved.getCamp() != null ? saved.getCamp().getCampName() : null);
        // Map doctorsList and staffList as in GET
        if (saved.getCamp() != null && saved.getCamp().getDoctorCamps() != null) {
            dto.setDoctorsList(saved.getCamp().getDoctorCamps().stream()
                .filter(dc -> dc.getDoctor() != null)
                .map(dc -> dc.getDoctor().getId())
                .toList());
        }
        if (saved.getCamp() != null && saved.getCamp().getVolunteerCamps() != null) {
            dto.setStaffList(saved.getCamp().getVolunteerCamps().stream()
                .filter(vc -> vc.getVolunteer() != null)
                .map(vc -> vc.getVolunteer().getId())
                .toList());
        }
        return ResponseEntity.ok(dto);
    }
}
