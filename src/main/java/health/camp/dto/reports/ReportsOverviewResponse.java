package health.camp.dto.reports;

import java.math.BigDecimal;

public class ReportsOverviewResponse {

    private long totalCamps;
    private long activeCamps;
    private long totalPatients;
    private long totalConsultations;
    private long totalPrescriptions;
    private BigDecimal totalCollection;
    private BigDecimal pendingPayments;
    private long totalDiscounts;
    private long doctorsCount;

    public long getTotalCamps() {
        return totalCamps;
    }

    public void setTotalCamps(long totalCamps) {
        this.totalCamps = totalCamps;
    }

    public long getActiveCamps() {
        return activeCamps;
    }

    public void setActiveCamps(long activeCamps) {
        this.activeCamps = activeCamps;
    }

    public long getTotalPatients() {
        return totalPatients;
    }

    public void setTotalPatients(long totalPatients) {
        this.totalPatients = totalPatients;
    }

    public long getTotalConsultations() {
        return totalConsultations;
    }

    public void setTotalConsultations(long totalConsultations) {
        this.totalConsultations = totalConsultations;
    }

    public long getTotalPrescriptions() {
        return totalPrescriptions;
    }

    public void setTotalPrescriptions(long totalPrescriptions) {
        this.totalPrescriptions = totalPrescriptions;
    }

    public BigDecimal getTotalCollection() {
        return totalCollection;
    }

    public void setTotalCollection(BigDecimal totalCollection) {
        this.totalCollection = totalCollection;
    }

    public BigDecimal getPendingPayments() {
        return pendingPayments;
    }

    public void setPendingPayments(BigDecimal pendingPayments) {
        this.pendingPayments = pendingPayments;
    }

    public long getTotalDiscounts() {
        return totalDiscounts;
    }

    public void setTotalDiscounts(long totalDiscounts) {
        this.totalDiscounts = totalDiscounts;
    }

    public long getDoctorsCount() {
        return doctorsCount;
    }

    public void setDoctorsCount(long doctorsCount) {
        this.doctorsCount = doctorsCount;
    }
}
