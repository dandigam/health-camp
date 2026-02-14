package health.camp.controller;

import health.camp.dto.camp.CampRequest;
import health.camp.dto.camp.CampResponse;
import health.camp.dto.camp.CampStatsResponse;
import health.camp.dto.doctor.DoctorResponse;
import health.camp.service.CampService;
import health.camp.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Camps", description = "Camp management APIs")
@RestController
@RequestMapping("/api/camps")
@CrossOrigin("*")
@RequiredArgsConstructor
public class CampController {

    private final CampService campService;
    private final DoctorService doctorService;

    @Operation(summary = "Create Camp (DRAFT or ACTIVE)")
    @PostMapping
    public ResponseEntity<CampResponse> create(@RequestBody CampRequest request) {
        return ResponseEntity.ok(campService.create(request));
    }

    @Operation(summary = " Update Camp (master only)")
    @PutMapping("/{id}")
    public ResponseEntity<CampResponse> update(@PathVariable Long id, @RequestBody CampRequest request) {
        return ResponseEntity.ok(campService.update(id, request));
    }

    @Operation(summary = "Start Camp (Draft → Active OR re-run")
    @PostMapping("/{id}/start")
    public ResponseEntity<CampResponse> startCamp(
            @PathVariable Long id,
            @RequestBody CampRequest request
    ) {
        return ResponseEntity.ok(campService.startCamp(id, request));
    }

    @Operation(summary = "Get Camp (with latest CampStart)")
    @GetMapping("/{id}")
    public ResponseEntity<CampResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(campService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<CampResponse>> getAllCamps() {
        return ResponseEntity.ok(campService.getAllCamps());
    }

    @Operation(summary = "Doctors for camp (ID cards)")
    @GetMapping("/{id}/doctors")
    public ResponseEntity<List<DoctorResponse>> getDoctors(@PathVariable String id) {
        return ResponseEntity.ok(doctorService.listByCampId(id));
    }
}
