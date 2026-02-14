package health.camp.model;

import java.util.Objects;

/**
 * Embedded objective data for SOAP notes (vitals).
 */
public class SOAPObjective {

    private Double weight;
    private String bp;
    private Integer pulse;
    private Double temp;
    private Integer spo2;
    private String notes;

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getBp() {
        return bp;
    }

    public void setBp(String bp) {
        this.bp = bp;
    }

    public Integer getPulse() {
        return pulse;
    }

    public void setPulse(Integer pulse) {
        this.pulse = pulse;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Integer getSpo2() {
        return spo2;
    }

    public void setSpo2(Integer spo2) {
        this.spo2 = spo2;
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
        SOAPObjective that = (SOAPObjective) o;
        return Objects.equals(weight, that.weight) && Objects.equals(bp, that.bp)
                && Objects.equals(pulse, that.pulse) && Objects.equals(temp, that.temp)
                && Objects.equals(spo2, that.spo2) && Objects.equals(notes, that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, bp, pulse, temp, spo2, notes);
    }
}
