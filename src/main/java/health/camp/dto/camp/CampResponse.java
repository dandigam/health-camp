package health.camp.dto.camp;

import health.camp.model.enums.CampStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class CampResponse {

    private Long id;
    private String name;
    private String village;
    private String district;
    private LocalDate planDate;
    private CampStatus campStatus;
    private List<Long> doctorIds;
    private List<Long> pharmacyIds;
    private List<Long> staffIds;
    private String organizerName;
    private String organizerPhone;
    private String organizerEmail;
    private String state;
    private String mandal;
    private String address;
    private String pinCode;

}
