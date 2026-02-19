package health.camp.controller;

import health.camp.dto.patient.PatientHistoryResponse;
import health.camp.dto.patient.PatientRequest;
import health.camp.dto.patient.PatientResponse;
import health.camp.service.PatientService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Patients", description = "Patient management APIs")
@RestController
@RequestMapping("/api/patients")
@Hidden
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @Operation(summary = "List patients")
    @GetMapping
    public ResponseEntity<Page<PatientResponse>> list(
            @RequestParam(required = false) String campId,
            @RequestParam(required = false) String search,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(patientService.list(campId, search, pageable));
    }

    @Operation(summary = "Get patient by ID")
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(patientService.getById(id));
    }

    @Operation(summary = "Patient history")
    @GetMapping("/{id}/history")
    public ResponseEntity<PatientHistoryResponse> getHistory(@PathVariable String id) {
        return ResponseEntity.ok(patientService.getHistory(id));
    }

    @Operation(summary = "Register patient")
    @PostMapping
    public ResponseEntity<PatientResponse> create(@Valid @RequestBody PatientRequest request) {
        return ResponseEntity.status(201).body(patientService.create(request));
    }
}
