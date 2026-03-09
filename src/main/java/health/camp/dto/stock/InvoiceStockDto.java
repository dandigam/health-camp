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

    // Strength (e.g., 50mg, 100mg) - used when creating new medicine
    private String strength;

    // Unit (e.g., mg, g, ml) - used when creating new medicine
    private String unit;

    // Manufacturer - used when creating new medicine
    private String manufacturer;

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

    private Integer currentStock;
}
