package health.camp.controller;

import health.camp.dto.prescription.DispenseRequest;
import health.camp.dto.prescription.DispenseResponse;
import health.camp.dto.prescription.PrescriptionResponse;
import health.camp.service.PrescriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Prescriptions", description = "Prescription and dispense APIs")
@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @Operation(summary = "List prescriptions")
    @GetMapping
    public ResponseEntity<Page<PrescriptionResponse>> list(
            @RequestParam(required = false) String campId,
            @RequestParam(required = false) String patientId,
            @RequestParam(required = false) String status,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(prescriptionService.list(campId, patientId, status, pageable));
    }

    @Operation(summary = "Get prescription by ID")
    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(prescriptionService.getById(id));
    }

    @Operation(summary = "Dispense prescription")
    @PatchMapping("/{id}/dispense")
    public ResponseEntity<DispenseResponse> dispense(@PathVariable String id, @Valid @RequestBody DispenseRequest request) {
        return ResponseEntity.ok(prescriptionService.dispense(id, request));
    }
}
