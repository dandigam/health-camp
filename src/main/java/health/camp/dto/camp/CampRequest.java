package health.camp.dto.camp;

import health.camp.model.enums.CampStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Data
public class CampRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String village;

    @NotBlank
    private String district;

    private CampStatus campStatus;

    private List<Long> doctorIds;
    private List<Long> staffIds;
    private List<Long> pharmacyIds = new ArrayList<>();

    private String organizerName;
    private String organizerPhone;
    private String organizerEmail;

    private LocalDate planDate;
}

