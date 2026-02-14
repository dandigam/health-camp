package health.camp.dto.prescription;

import health.camp.model.enums.PrescriptionStatus;

import java.time.Instant;
import java.util.List;

public class PrescriptionResponse {

    private String id;
    private String consultationId;
    private String patientId;
    private String doctorId;
    private String campId;
    private List<PrescriptionItemResponse> items;
    private PrescriptionStatus status;
    private Instant createdAt;
    private String patientName;
    private String doctorName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(String consultationId) {
        this.consultationId = consultationId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getCampId() {
        return campId;
    }

    public void setCampId(String campId) {
        this.campId = campId;
    }

    public List<PrescriptionItemResponse> getItems() {
        return items;
    }

    public void setItems(List<PrescriptionItemResponse> items) {
        this.items = items;
    }

    public PrescriptionStatus getStatus() {
        return status;
    }

    public void setStatus(PrescriptionStatus status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public static class PrescriptionItemResponse {
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
    }
}
