package health.camp.model;

import java.util.Objects;

/**
 * Embedded item in a Prescription.
 */
public class PrescriptionItem {

    private String medicineId;
    private String medicineName;
    private int quantity;
    private int morning;
    private int afternoon;
    private int night;
    private int days;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getMorning() {
        return morning;
    }

    public void setMorning(int morning) {
        this.morning = morning;
    }

    public int getAfternoon() {
        return afternoon;
    }

    public void setAfternoon(int afternoon) {
        this.afternoon = afternoon;
    }

    public int getNight() {
        return night;
    }

    public void setNight(int night) {
        this.night = night;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrescriptionItem that = (PrescriptionItem) o;
        return quantity == that.quantity && morning == that.morning && afternoon == that.afternoon
                && night == that.night && days == that.days
                && Objects.equals(medicineId, that.medicineId)
                && Objects.equals(medicineName, that.medicineName)
                && Objects.equals(notes, that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medicineId, medicineName, quantity, morning, afternoon, night, days, notes);
    }
}
