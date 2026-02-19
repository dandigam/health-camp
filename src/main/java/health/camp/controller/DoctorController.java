package health.camp.controller;

import health.camp.dto.doctor.DoctorRequest;
import health.camp.dto.doctor.DoctorResponse;
import health.camp.service.DoctorService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Doctors", description = "Doctor management APIs")
@RestController
@RequestMapping("/api/doctors")
@CrossOrigin("*")
@Hidden
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @Operation(summary = "List doctors")
    @GetMapping
    public ResponseEntity<List<DoctorResponse>> list(
            @RequestParam(required = false) String campId,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean active) {
        return ResponseEntity.ok(doctorService.list(campId, search, active));
    }

    @Operation(summary = "Get doctor by ID")
    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.getById(id));
    }

    @Operation(summary = "Add doctor")
    @PostMapping
    public ResponseEntity<DoctorResponse> create(@Valid @RequestBody DoctorRequest request) {
        return ResponseEntity.status(201).body(doctorService.create(request));
    }

    @Operation(summary = "Update doctor")
    @PatchMapping("/{id}")
    public ResponseEntity<DoctorResponse> update(@PathVariable Long id, @RequestBody DoctorRequest request) {
        return ResponseEntity.ok(doctorService.update(id, request));
    }
}
