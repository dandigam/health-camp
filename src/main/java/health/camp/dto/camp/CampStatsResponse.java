package health.camp.dto.camp;

import java.math.BigDecimal;

public class CampStatsResponse {

    private long totalPatients;
    private long patientsAtDoctor;
    private long patientsAtPharmacy;
    private long patientsAtCashier;
    private long exitedPatients;
    private BigDecimal totalCollection;

    public CampStatsResponse() {
    }

    public long getTotalPatients() {
        return totalPatients;
    }

    public void setTotalPatients(long totalPatients) {
        this.totalPatients = totalPatients;
    }

    public long getPatientsAtDoctor() {
        return patientsAtDoctor;
    }

    public void setPatientsAtDoctor(long patientsAtDoctor) {
        this.patientsAtDoctor = patientsAtDoctor;
    }

    public long getPatientsAtPharmacy() {
        return patientsAtPharmacy;
    }

    public void setPatientsAtPharmacy(long patientsAtPharmacy) {
        this.patientsAtPharmacy = patientsAtPharmacy;
    }

    public long getPatientsAtCashier() {
        return patientsAtCashier;
    }

    public void setPatientsAtCashier(long patientsAtCashier) {
        this.patientsAtCashier = patientsAtCashier;
    }

    public long getExitedPatients() {
        return exitedPatients;
    }

    public void setExitedPatients(long exitedPatients) {
        this.exitedPatients = exitedPatients;
    }

    public BigDecimal getTotalCollection() {
        return totalCollection;
    }

    public void setTotalCollection(BigDecimal totalCollection) {
        this.totalCollection = totalCollection;
    }
}
