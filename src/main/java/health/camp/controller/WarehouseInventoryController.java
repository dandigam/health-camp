package health.camp.controller;

import health.camp.dto.inventory.WarehouseInventoryDto;
import health.camp.service.WarehouseInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouse-inventory")
@RequiredArgsConstructor
@CrossOrigin("*")
public class WarehouseInventoryController {

    private final WarehouseInventoryService inventoryService;

    /**
     * Get all inventory for a warehouse
     * GET /api/warehouse-inventory/warehouse/{warehouseId}
     */
    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<WarehouseInventoryDto>> getInventoryByWarehouse(
            @PathVariable Long warehouseId) {
        return ResponseEntity.ok(inventoryService.getInventoryByWarehouse(warehouseId));
    }

    /**
     * Get low stock items (qty <= minimumQty)
     * GET /api/warehouse-inventory/warehouse/{warehouseId}/low-stock
     */
    @GetMapping("/warehouse/{warehouseId}/low-stock")
    public ResponseEntity<List<WarehouseInventoryDto>> getLowStockItems(
            @PathVariable Long warehouseId) {
        return ResponseEntity.ok(inventoryService.getLowStockItems(warehouseId));
    }
}
