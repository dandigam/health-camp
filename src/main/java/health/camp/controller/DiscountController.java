package health.camp.controller;

import health.camp.dto.discount.DiscountResponse;
import health.camp.service.DiscountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Discounts", description = "Discount APIs")
@RestController
@RequestMapping("/api/discounts")
public class DiscountController {

    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @Operation(summary = "List discounts")
    @GetMapping
    public ResponseEntity<Page<DiscountResponse>> list(
            @RequestParam(required = false) String campId,
            @RequestParam(required = false) String patientId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(discountService.list(campId, patientId, pageable));
    }
}
