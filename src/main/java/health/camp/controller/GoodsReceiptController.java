package health.camp.controller;

import health.camp.dto.goods.GoodsReceiptDto;
import health.camp.service.GoodsReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchase-orders")
@RequiredArgsConstructor
@CrossOrigin("*")
public class GoodsReceiptController {
    private final GoodsReceiptService goodsReceiptService;

    @PostMapping("/{id}/receive")
    public ResponseEntity<GoodsReceiptDto> receiveGoods(
            @PathVariable Long id,
            @RequestBody GoodsReceiptDto dto) {
        return ResponseEntity.ok(goodsReceiptService.createGoodsReceipt(id, dto));
    }
}
