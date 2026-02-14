package health.camp.dto.stock;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

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

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public String getCampId() {
        return campId;
    }

    public void setCampId(String campId) {
        this.campId = campId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }
}
