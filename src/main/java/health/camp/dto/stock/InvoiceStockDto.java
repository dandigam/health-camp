package health.camp.dto.stock;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceStockDto {

    private Long id;

    private Long invoiceId;

    // Medicine ID - required only if isAlreadyExist is true
    private Long medicineId;

    // Medicine name - required if isAlreadyExist is false (for creating new medicine)
    private String medicineName;

    // Medicine type (tablet, syrup, etc.) - used when creating new medicine
    private String medicineType;

    // Flag to indicate if medicine already exists in DB
    // true = use medicineId, false = create new medicine using medicineName
    @Builder.Default
    private Boolean isAlreadyExist = true;

    private String hsnNo;

    private String batchNo;

    private LocalDate expDate;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Warehouse ID is required")
    private Long warehouseId;

    private String warehouseName;
}
