package health.camp.controller;

import health.camp.dto.EncounterClinicalDto;
import health.camp.dto.EncounterDto;
import health.camp.dto.PatientSummaryDto;
import health.camp.entity.Encounter;
import health.camp.model.enums.EncounterStatus;
import health.camp.service.EncounterService;
import health.camp.service.EncounterSubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/encounters")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EncounterController {
    private final EncounterService encounterService;
    private final EncounterSubjectService encounterSubjectService;

    @PostMapping
    public ResponseEntity<EncounterDto> createEncounter(@RequestBody EncounterDto dto) {
        Encounter saved = encounterService.saveEncounter(encounterService.toEntity(dto));
        return ResponseEntity.ok(encounterService.toDto(saved));
    }

    @PostMapping("/clinical")
    public void saveClinicalData(@RequestBody EncounterClinicalDto dto) {
        encounterService.saveClinicalData(dto);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EncounterDto> getEncounterById(@PathVariable Long id) {
        return encounterService.getEncounterById(id)
                .map(encounter -> {
                    EncounterDto dto = encounterService.toDto(encounter);
                    encounterSubjectService.getByEncounterId(id)
                            .ifPresent(dto::setEncounterSubject);
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEncounter(@PathVariable Long id) {
        encounterService.deleteEncounter(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/camp-event/{campEventId}/patients")
    public ResponseEntity<List<PatientSummaryDto>> getPatientsByCampEvent(@PathVariable Long campEventId) {
        List<PatientSummaryDto> patients = encounterService.getPatientSummariesByCampEventId(campEventId);
        return ResponseEntity.ok(patients);
    }
}
