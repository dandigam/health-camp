package health.camp.dto.supplier;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import health.camp.dto.stock.InvoiceDocumentDto;

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

    // Invoice info (input for receiving stock)
    private String invoiceNumber;
    private LocalDate invoiceDate;
    private BigDecimal invoiceAmount;
    private String paymentMode;

    // Invoice response fields
    private Long invoiceId;
    private String invoiceType;
    private String invoiceCreatedAt;

    // Invoice documents
    private List<InvoiceDocumentDto> invoiceDocuments;

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
        private Integer currentQty;
        private Integer requestedQuantity;
        private Integer receivedQuantity;
        private Double unitPrice;
        private String status;
    }
}
