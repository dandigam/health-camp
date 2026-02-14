package health.camp.controller;

import health.camp.dto.consultation.ConsultationRequest;
import health.camp.dto.consultation.ConsultationResponse;
import health.camp.service.ConsultationService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Consultations", description = "Consultation APIs")
@RestController
@RequestMapping("/api/consultations")
public class ConsultationController {

    private final ConsultationService consultationService;

    public ConsultationController(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    @Operation(summary = "List consultations")
    @GetMapping
    public ResponseEntity<Page<ConsultationResponse>> list(
            @RequestParam(required = false) String campId,
            @RequestParam(required = false) String patientId,
            @RequestParam(required = false) String doctorId,
            @RequestParam(required = false) String status,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(consultationService.list(campId, patientId, doctorId, status, pageable));
    }

    @Operation(summary = "Get consultation by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ConsultationResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(consultationService.getById(id));
    }

    @Operation(summary = "Create consultation")
    @PostMapping
    public ResponseEntity<ConsultationResponse> create(@Valid @RequestBody ConsultationRequest request) {
        return ResponseEntity.status(201).body(consultationService.create(request));
    }

    @Operation(summary = "Update consultation")
    @PatchMapping("/{id}")
    public ResponseEntity<ConsultationResponse> update(@PathVariable String id, @RequestBody ConsultationRequest request) {
        return ResponseEntity.ok(consultationService.update(id, request));
    }
}
