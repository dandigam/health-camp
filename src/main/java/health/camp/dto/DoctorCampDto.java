package health.camp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorCampDto {
    private Long doctorId;
    private Long campId;
    private Boolean isDefault;
    private Boolean active;
}
