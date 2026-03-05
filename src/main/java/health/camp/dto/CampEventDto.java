package health.camp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampEventDto {
    private Long id;
    private Long campId;
    private String status;
    private String campName;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Long> doctorsList;
    private List<Long> staffList;
    private String state;
    private String district;
    private String mandal;
    private String city;
    private String address;
    private String pinCode;
}
