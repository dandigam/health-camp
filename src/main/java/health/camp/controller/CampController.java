package health.camp.controller;

import health.camp.dto.CampDto;
import health.camp.entity.Camp;
import health.camp.service.CampService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/camp-templates")
@RequiredArgsConstructor
@CrossOrigin("*")  // Allow CORS for all origins (adjust as needed)
public class CampController {
    private final CampService campService;

    @PostMapping
    public ResponseEntity<CampDto> addOrUpdateCamp(@RequestBody CampDto campDto) {
        Camp camp = campService.saveCampFromDto(campDto);
        CampDto responseDto = toDto(camp);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<CampDto>> getAllCamps() {
        List<Camp> camps = campService.getAllCamps();
        List<CampDto> dtos = camps.stream().map(this::toDto).toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampDto> getCampById(@PathVariable Long id) {
        Camp camp = campService.getCampById(id);
        if (camp == null) {
            return ResponseEntity.notFound().build();
        }
        CampDto dto = toDto(camp);
        return ResponseEntity.ok(dto);
    }

    private CampDto toDto(Camp camp) {
        CampDto dto = new CampDto();
        dto.setId(camp.getId());
        dto.setCampName(camp.getCampName());
        dto.setOrganizerName(camp.getOrganizerName());
        dto.setOrganizerPhone(camp.getOrganizerPhone());
        dto.setOrganizerEmail(camp.getOrganizerEmail());
        dto.setActive(camp.getActive());
        dto.setStateId(camp.getStateId());
        dto.setState(camp.getState());
        dto.setDistrictId(camp.getDistrictId());
        dto.setDistrict(camp.getDistrict());
        dto.setMandalId(camp.getMandalId());
        dto.setMandal(camp.getMandal());
        dto.setCity(camp.getCity());
        dto.setAddress(camp.getAddress());
        dto.setPinCode(camp.getPinCode());
        if (camp.getDoctorCamps() != null) {
            dto.setDoctorList(camp.getDoctorCamps().stream()
                .map(dc -> dc.getDoctor() != null ? dc.getDoctor().getId() : null)
                .filter(id -> id != null)
                .toList());
        } else {
            dto.setDoctorList(java.util.Collections.emptyList());
        }
        if (camp.getVolunteerCamps() != null) {
            dto.setStaffList(camp.getVolunteerCamps().stream()
                .map(vc -> vc.getVolunteer() != null ? vc.getVolunteer().getId() : null)
                .filter(id -> id != null)
                .toList());
        } else {
            dto.setStaffList(java.util.Collections.emptyList());
        }
        return dto;
    }
}
