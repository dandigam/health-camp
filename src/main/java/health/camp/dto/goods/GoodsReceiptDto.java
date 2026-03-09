package health.camp.dto.goods;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsReceiptDto {
    private Long id;
    private String receiptNumber;
    private Long requestId;
    private Long warehouseId;
    private String receivedBy;
    private LocalDate receivedDate;
    private String invoiceNumber;
    private BigDecimal invoiceAmount;
    private List<GoodsReceiptItemDto> items;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class GoodsReceiptItemDto {
        private Long id;
        private Long receiptId;
        private Long requestItemId;
        private Long medicineId;
        private Integer receivedQty;
        private String batchNumber;
        private LocalDate expiryDate;
    }
}
