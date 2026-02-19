package health.camp.controller;

import health.camp.dto.SupplierDto;
import health.camp.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping("/warehouse/{warehouseId}/with-medicines")
    public ResponseEntity<SupplierDto> addSupplierWithMedicines(
            @PathVariable Long warehouseId,
            @RequestBody SupplierDto dto) {
        return ResponseEntity.ok(supplierService.addSupplierWithMedicines(warehouseId, dto));
    }


    @PutMapping("/warehouse/{warehouseId}/supplier/{id}")
    public ResponseEntity<SupplierDto> updateSupplier(
            @PathVariable Long warehouseId,
            @PathVariable Long id,
            @RequestBody SupplierDto dto) {
        return ResponseEntity.ok(supplierService.updateSupplier(warehouseId, id, dto));
    }

    @DeleteMapping("/warehouse/{warehouseId}/supplier/{id}")
    public ResponseEntity<Void> deleteSupplier(
            @PathVariable Long warehouseId,
            @PathVariable Long id) {
        supplierService.deleteSupplier(warehouseId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDto> getSupplier(@PathVariable Long id) {
        return ResponseEntity.ok(supplierService.getSupplier(id));
    }

    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<SupplierDto>> getSuppliersByWarehouse(@PathVariable Long warehouseId) {
        return ResponseEntity.ok(supplierService.getSuppliersByWarehouse(warehouseId));
    }

    @DeleteMapping("/warehouse/{warehouseId}/supplier/{supplierId}/medicine/{medicineId}")
    public ResponseEntity<Void> removeMedicineFromSupplier(
            @PathVariable Long warehouseId,
            @PathVariable Long supplierId,
            @PathVariable Long medicineId) {
        supplierService.removeMedicineFromSupplier(warehouseId, supplierId, medicineId);
        return ResponseEntity.noContent().build();
    }
}
