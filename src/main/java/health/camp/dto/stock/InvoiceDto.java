package health.camp.dto.stock;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceDto {

    private Long id;

    @NotNull(message = "Supplier ID is required")
    private Long supplierId;

    private String supplierName;

    private String paymentMode;

    private String invoiceNumber;

    private BigDecimal invoiceAmount;

    @NotNull(message = "Invoice date is required")
    private LocalDate invoiceDate;

  
    private Long warehouseId;

    private String warehouseName;

    @Valid
    private List<InvoiceStockDto> items;

    private String createdBy;

    private String createdAt;
}
