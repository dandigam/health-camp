package health.camp.controller;

import health.camp.dto.medicine.MedicineResponse;
import health.camp.service.MedicineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Medicines", description = "Medicine master data APIs")
@RestController
@RequestMapping("/api/medicines")
public class MedicineController {

    private final MedicineService medicineService;

    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @Operation(summary = "List medicines")
    @GetMapping
    public ResponseEntity<Page<MedicineResponse>> list(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(medicineService.list(search, category, pageable));
    }

    @Operation(summary = "Get medicine by ID")
    @GetMapping("/{id}")
    public ResponseEntity<MedicineResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(medicineService.getById(id));
    }
}
