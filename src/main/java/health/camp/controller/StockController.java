package health.camp.controller;

import health.camp.dto.stock.StockItemRequest;
import health.camp.dto.stock.StockItemResponse;
import health.camp.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Stock", description = "Stock management APIs")
@RestController
@RequestMapping("/api/stock")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @Operation(summary = "List stock")
    @GetMapping
    public ResponseEntity<Page<StockItemResponse>> list(
            @RequestParam(required = false) String campId,
            @RequestParam(required = false) String medicineId,
            @RequestParam(required = false) Boolean lowStockOnly,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(stockService.list(campId, medicineId, lowStockOnly, pageable));
    }

    @Operation(summary = "Add stock")
    @PostMapping
    public ResponseEntity<StockItemResponse> create(@Valid @RequestBody StockItemRequest request) {
        return ResponseEntity.status(201).body(stockService.create(request));
    }
}
