package health.camp.dto.supplier;

import lombok.*;

import java.util.List;

import health.camp.dto.stock.DocumentSummary;
import health.camp.dto.stock.InvoiceDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierOrderRequestDto {

    private Long id;
    private Long warehouseId;
    private Long supplierId;
    private String supplierName;
    private String warehouseName;
    private String status;
    private String purchaseOrder;
    private String createdAt;
    private String updatedAt;
    private Integer itemCount;
    private List<ItemDto> items;

    private Boolean isPriority;
    private InvoiceDto invoice;

    private List<DocumentSummary> documents;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ItemDto {
        private Long id;
        private Long medicineId;
        private String medicineName;
        private String medicineType;
        private String strength;
        private String unit;
        private String manufacturer;
        private Integer currentQty;
        private Integer requestedQuantity;
        private Integer receivedQuantity;
        private Double unitPrice;
        private String status;
    }
}
