package health.camp.dto.user;

import lombok.Data;

@Data
public class UserCampEventSimpleDto {
    private Long id;
    private Long campId;
    private String campName;
    private String eventDate; // ISO string or format as needed
}
