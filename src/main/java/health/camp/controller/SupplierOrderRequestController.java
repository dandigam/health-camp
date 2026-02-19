package health.camp.controller;

import health.camp.dto.supplier.MonthlyOrderTrackingDto;
import health.camp.dto.supplier.SupplierOrderRequestDto;
import health.camp.service.SupplierOrderRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplier-orders")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SupplierOrderRequestController {

    private final SupplierOrderRequestService supplierOrderRequestService;

    /**
     * Create a new supplier order request
     * POST /api/supplier-orders
     * Body: { warehouseId, supplierId, items: [{ medicineId, requestedQuantity, unitPrice }] }
     */
    @PostMapping
    public ResponseEntity<SupplierOrderRequestDto> createOrderRequest(
            @RequestBody SupplierOrderRequestDto dto) {
        return ResponseEntity.ok(supplierOrderRequestService.createOrderRequest(dto));
    }

    /**
     * Update an existing order request (add items, update quantities, etc.)
     * PUT /api/supplier-orders/{requestId}
     */
    @PutMapping("/{requestId}")
    public ResponseEntity<SupplierOrderRequestDto> updateOrderRequest(
            @PathVariable Long requestId,
            @RequestBody SupplierOrderRequestDto dto) {
        return ResponseEntity.ok(supplierOrderRequestService.updateOrderRequest(requestId, dto));
    }

    /**
     * Update order request status
     * PATCH /api/supplier-orders/{requestId}/status?status=PENDING
     */
    @PatchMapping("/{requestId}/status")
    public ResponseEntity<SupplierOrderRequestDto> updateRequestStatus(
            @PathVariable Long requestId,
            @RequestParam String status) {
        return ResponseEntity.ok(supplierOrderRequestService.updateRequestStatus(requestId, status));
    }

    /**
     * Get a specific order request by ID
     * GET /api/supplier-orders/{requestId}
     */
    @GetMapping("/{requestId}")
    public ResponseEntity<SupplierOrderRequestDto> getOrderRequest(
            @PathVariable Long requestId) {
        return ResponseEntity.ok(supplierOrderRequestService.getOrderRequest(requestId));
    }

    /**
     * Get all order requests for a warehouse
     * GET /api/supplier-orders/warehouse/{warehouseId}
     */
    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<SupplierOrderRequestDto>> getOrderRequestsByWarehouse(
            @PathVariable Long warehouseId) {
        return ResponseEntity.ok(supplierOrderRequestService.getOrderRequestsByWarehouse(warehouseId));
    }

    /**
     * Get order requests by warehouse and status
     * GET /api/supplier-orders/warehouse/{warehouseId}/status?status=PENDING
     */
    @GetMapping("/warehouse/{warehouseId}/status")
    public ResponseEntity<List<SupplierOrderRequestDto>> getOrderRequestsByWarehouseAndStatus(
            @PathVariable Long warehouseId,
            @RequestParam String status) {
        return ResponseEntity.ok(supplierOrderRequestService.getOrderRequestsByWarehouseAndStatus(warehouseId, status));
    }

    /**
     * Get monthly order tracking for a warehouse
     * GET /api/supplier-orders/warehouse/{warehouseId}/monthly-tracking
     */
    @GetMapping("/warehouse/{warehouseId}/monthly-tracking")
    public ResponseEntity<List<MonthlyOrderTrackingDto>> getMonthlyOrderTracking(
            @PathVariable Long warehouseId) {
        return ResponseEntity.ok(supplierOrderRequestService.getMonthlyOrderTracking(warehouseId));
    }

    /**
     * Delete an order request
     * DELETE /api/supplier-orders/{requestId}
     */
    @DeleteMapping("/{requestId}")
    public ResponseEntity<Void> deleteOrderRequest(@PathVariable Long requestId) {
        supplierOrderRequestService.deleteOrderRequest(requestId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Remove a specific item from an order request
     * DELETE /api/supplier-orders/{requestId}/items/{itemId}
     */
    @DeleteMapping("/{requestId}/items/{itemId}")
    public ResponseEntity<Void> removeItemFromRequest(
            @PathVariable Long requestId,
            @PathVariable Long itemId) {
        supplierOrderRequestService.removeItemFromRequest(requestId, itemId);
        return ResponseEntity.noContent().build();
    }
}
