package health.camp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampDto {
    private Long id;
    private String campName;
    private String organizerName;
    private String organizerPhone;
    private String organizerEmail;
    private Boolean active;

    private Long stateId;
    private String state;
    private Long districtId;
    private String district;
    private Long mandalId;
    private String mandal;
    private String city;
    private String address;
    private String pinCode;
    private List<Long> doctorList;
    private List<Long> staffList;
}
