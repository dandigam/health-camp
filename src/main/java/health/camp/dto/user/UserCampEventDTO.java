package health.camp.dto.user;

import lombok.Data;

@Data
public class UserCampEventDTO {
    private Long campEventId;
    private Long campId;
    private String campName;
    private String eventDate; 
}
