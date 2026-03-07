package health.camp.controller;

import health.camp.dto.stock.InvoiceDocumentDto;
import health.camp.dto.supplier.MonthlyOrderTrackingDto;
import health.camp.dto.supplier.SupplierOrderRequestDto;
import health.camp.service.InvoiceDocumentService;
import health.camp.service.SupplierOrderRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/supplier-orders")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SupplierOrderRequestController {

    private final SupplierOrderRequestService supplierOrderRequestService;

    private final InvoiceDocumentService invoiceDocumentService;

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

    // ===== Invoice Document endpoints for Supplier Orders =====

    /**
     * Upload one or multiple invoice documents (PDF, images) for a supplier order's invoice
     * POST /api/supplier-orders/{requestId}/invoice/documents
     */
    @PostMapping(value = "/{requestId}/invoice/documents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<InvoiceDocumentDto>> uploadInvoiceDocuments(
            @PathVariable Long requestId,
            @RequestParam("files") MultipartFile[] files) {
        Long invoiceId = supplierOrderRequestService.getInvoiceIdByRequestId(requestId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(invoiceDocumentService.uploadDocuments(invoiceId, files));
    }

    /**
     * Get all invoice documents for a supplier order
     * GET /api/supplier-orders/{requestId}/invoice/documents
     */
    @GetMapping("/{requestId}/invoice/documents")
    public ResponseEntity<List<InvoiceDocumentDto>> getInvoiceDocuments(@PathVariable Long requestId) {
        Long invoiceId = supplierOrderRequestService.getInvoiceIdByRequestId(requestId);
        return ResponseEntity.ok(invoiceDocumentService.getDocumentsByInvoiceId(invoiceId));
    }

    /**
     * Download a specific invoice document
     * GET /api/supplier-orders/invoice/documents/{documentId}/download
     */
    @GetMapping("/invoice/documents/{documentId}/download")
    public ResponseEntity<Resource> downloadInvoiceDocument(@PathVariable Long documentId) {
        Resource resource = invoiceDocumentService.loadFileAsResource(documentId);
        String contentType = invoiceDocumentService.getContentType(documentId);
        String originalFileName = invoiceDocumentService.getOriginalFileName(documentId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType != null ? contentType : "application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + originalFileName + "\"")
                .body(resource);
    }

    /**
     * Delete a specific invoice document
     * DELETE /api/supplier-orders/invoice/documents/{documentId}
     */
    @DeleteMapping("/invoice/documents/{documentId}")
    public ResponseEntity<Void> deleteInvoiceDocument(@PathVariable Long documentId) {
        invoiceDocumentService.deleteDocument(documentId);
        return ResponseEntity.noContent().build();
    }
}
