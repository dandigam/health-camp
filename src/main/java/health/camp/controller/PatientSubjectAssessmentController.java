package health.camp.controller;


import health.camp.dto.EncounterSubjectDto;
import health.camp.service.EncounterSubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/subject")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PatientSubjectAssessmentController {

    private final EncounterSubjectService service;

    @GetMapping("/encounter/{encounterId}")
    public Optional<EncounterSubjectDto> getByEncounterId(
            @PathVariable Long encounterId) {
        return service.getByEncounterId(encounterId);
    }

        @PostMapping
        public EncounterSubjectDto save(
                @RequestBody EncounterSubjectDto dto) {
            return service.saveOrUpdate(dto);
        }







}
