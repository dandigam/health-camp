package health.camp.controller;

import health.camp.dto.stock.InvoiceDto;
import health.camp.dto.stock.InvoiceStockDto;
import health.camp.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
@CrossOrigin("*")
public class InvoiceController {

    private final InvoiceService invoiceService;

    // Invoice endpoints

    @PostMapping
    public ResponseEntity<InvoiceDto> createInvoice(@Valid @RequestBody InvoiceDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(invoiceService.createInvoice(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceDto> updateInvoice(
            @PathVariable Long id,
            @Valid @RequestBody InvoiceDto dto) {
        return ResponseEntity.ok(invoiceService.updateInvoice(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDto> getInvoiceById(@PathVariable Long id) {
        return ResponseEntity.ok(invoiceService.getInvoiceById(id));
    }

    @GetMapping
    public ResponseEntity<List<InvoiceDto>> getAllInvoices() {
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }

    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<InvoiceDto>> getInvoicesByWarehouse(@PathVariable Long warehouseId) {
        return ResponseEntity.ok(invoiceService.getInvoicesByWarehouse(warehouseId));
    }

    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<List<InvoiceDto>> getInvoicesBySupplier(@PathVariable Long supplierId) {
        return ResponseEntity.ok(invoiceService.getInvoicesBySupplier(supplierId));
    }

    // Invoice Stock endpoints

    @PostMapping("/{invoiceId}/stocks")
    public ResponseEntity<InvoiceStockDto> addStockItem(
            @PathVariable Long invoiceId,
            @Valid @RequestBody InvoiceStockDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(invoiceService.addStockItem(invoiceId, dto));
    }

    @PutMapping("/stocks/{stockId}")
    public ResponseEntity<InvoiceStockDto> updateStockItem(
            @PathVariable Long stockId,
            @Valid @RequestBody InvoiceStockDto dto) {
        return ResponseEntity.ok(invoiceService.updateStockItem(stockId, dto));
    }

    @DeleteMapping("/stocks/{stockId}")
    public ResponseEntity<Void> deleteStockItem(@PathVariable Long stockId) {
        invoiceService.deleteStockItem(stockId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{invoiceId}/stocks")
    public ResponseEntity<List<InvoiceStockDto>> getStockItemsByInvoice(@PathVariable Long invoiceId) {
        return ResponseEntity.ok(invoiceService.getStockItemsByInvoice(invoiceId));
    }

    @GetMapping("/stocks/warehouse/{warehouseId}")
    public ResponseEntity<List<InvoiceStockDto>> getStockItemsByWarehouse(@PathVariable Long warehouseId) {
        return ResponseEntity.ok(invoiceService.getStockItemsByWarehouse(warehouseId));
    }

    @GetMapping("/stocks/warehouse/{warehouseId}/expired")
    public ResponseEntity<List<InvoiceStockDto>> getExpiredStock(@PathVariable Long warehouseId) {
        return ResponseEntity.ok(invoiceService.getExpiredStock(warehouseId));
    }

    @GetMapping("/stocks/warehouse/{warehouseId}/expiring")
    public ResponseEntity<List<InvoiceStockDto>> getExpiringStock(
            @PathVariable Long warehouseId,
            @RequestParam(defaultValue = "30") int daysAhead) {
        return ResponseEntity.ok(invoiceService.getExpiringStock(warehouseId, daysAhead));
    }
}
