package health.camp.dto.user;

import lombok.Data;

@Data
public class UserContextDTO {
    private Long campEventId;
    private Long campId;
    private String campName;
    private Long warehouseId;
}
