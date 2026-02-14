package health.camp.dto.prescription;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public class DispenseRequest {

    @Valid
    private List<DispensedItemDto> dispensedItems;

    @Valid
    @NotNull(message = "payment is required")
    private PaymentDto payment;

    public List<DispensedItemDto> getDispensedItems() {
        return dispensedItems;
    }

    public void setDispensedItems(List<DispensedItemDto> dispensedItems) {
        this.dispensedItems = dispensedItems;
    }

    public PaymentDto getPayment() {
        return payment;
    }

    public void setPayment(PaymentDto payment) {
        this.payment = payment;
    }

    public static class DispensedItemDto {
        @NotNull
        private String medicineId;
        @NotNull
        private Integer quantity;

        public String getMedicineId() {
            return medicineId;
        }

        public void setMedicineId(String medicineId) {
            this.medicineId = medicineId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }

    public static class PaymentDto {
        private String paymentType = "full";
        @NotNull
        @DecimalMin("0")
        private BigDecimal paidAmount;
        @NotNull
        @DecimalMin("0")
        private BigDecimal totalAmount;
        @NotNull
        @DecimalMin("0")
        private BigDecimal pendingAmount;
        private String status;

        public String getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(String paymentType) {
            this.paymentType = paymentType;
        }

        public BigDecimal getPaidAmount() {
            return paidAmount;
        }

        public void setPaidAmount(BigDecimal paidAmount) {
            this.paidAmount = paidAmount;
        }

        public BigDecimal getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
        }

        public BigDecimal getPendingAmount() {
            return pendingAmount;
        }

        public void setPendingAmount(BigDecimal pendingAmount) {
            this.pendingAmount = pendingAmount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
