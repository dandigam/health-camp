package health.camp.dto.consultation;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PrescriptionItemDto {

    @NotBlank(message = "medicineId is required")
    private String medicineId;

    @NotBlank(message = "medicineName is required")
    @Size(max = 255)
    private String medicineName;

    @NotNull(message = "quantity is required")
    @Min(1)
    private Integer quantity;

    @NotNull(message = "morning is required")
    @Min(0)
    @Max(3)
    private Integer morning;

    @NotNull(message = "afternoon is required")
    @Min(0)
    @Max(3)
    private Integer afternoon;

    @NotNull(message = "night is required")
    @Min(0)
    @Max(3)
    private Integer night;

    @NotNull(message = "days is required")
    @Min(1)
    private Integer days;

    @Size(max = 500)
    private String notes;

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getMorning() {
        return morning;
    }

    public void setMorning(Integer morning) {
        this.morning = morning;
    }

    public Integer getAfternoon() {
        return afternoon;
    }

    public void setAfternoon(Integer afternoon) {
        this.afternoon = afternoon;
    }

    public Integer getNight() {
        return night;
    }

    public void setNight(Integer night) {
        this.night = night;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
