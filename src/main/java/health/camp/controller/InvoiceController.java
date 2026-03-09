package health.camp.controller;

import health.camp.dto.stock.InvoiceDto;
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

    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<InvoiceDto>> getInvoicesByWarehouse(@PathVariable Long warehouseId) {
        return ResponseEntity.ok(invoiceService.getInvoicesByWarehouse(warehouseId));
    }

    // Invoice endpoints

    @PostMapping
    public ResponseEntity<InvoiceDto> saveInvoice(@Valid @RequestBody InvoiceDto dto) {
        return ResponseEntity.status(dto.getId() == null ? HttpStatus.CREATED : HttpStatus.OK)
                .body(invoiceService.saveInvoice(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceDto> updateInvoice(
            @PathVariable Long id,
            @Valid @RequestBody InvoiceDto dto) {
        dto.setId(id);
        return ResponseEntity.ok(invoiceService.saveInvoice(dto));
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



}
