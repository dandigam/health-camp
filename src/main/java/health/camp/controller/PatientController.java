package health.camp.controller;

import health.camp.dto.patient.PatientRequest;
import health.camp.dto.patient.PatientResponse;
import health.camp.entity.MedicalConditionLookup;
import health.camp.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

import java.util.List;

@Tag(name = "Patients", description = "Patient management APIs")
@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @Operation(summary = "List all patients with optional search")
    @GetMapping
    public ResponseEntity<Page<PatientResponse>> list(
            @RequestParam(required = false) String search,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(patientService.list(search, pageable));
    }

    @Operation(summary = "Get patient by ID")
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getById(id));
    }

    @Operation(summary = "Create or update patient (if id is provided, updates; otherwise creates)")
    @PostMapping
    public ResponseEntity<PatientResponse> save(@Valid @RequestBody PatientRequest request) {
        PatientResponse response = patientService.save(request);
        int status = request.getId() == null ? 201 : 200;
        return ResponseEntity.status(status).body(response);
    }

    @Operation(summary = "Get all medical conditions for lookup")
    @GetMapping("/medical-conditions")
    public ResponseEntity<List<MedicalConditionLookup>> getMedicalConditions() {
        return ResponseEntity.ok(patientService.getMedicalConditions());
    }
}
