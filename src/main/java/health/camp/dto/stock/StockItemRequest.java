package health.camp.dto.stock;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class StockItemRequest {

    @NotBlank(message = "medicineId is required")
    private String medicineId;

    @NotBlank(message = "campId is required")
    private String campId;

    @NotNull(message = "quantity is required")
    @Min(0)
    private Integer quantity;

    @NotBlank(message = "batchNumber is required")
    @Size(max = 100)
    private String batchNumber;

    @NotNull(message = "expiryDate is required")
    private LocalDate expiryDate;

    @NotNull(message = "purchaseDate is required")
    private LocalDate purchaseDate;

    @NotBlank(message = "supplierId is required")
    private String supplierId;
}
