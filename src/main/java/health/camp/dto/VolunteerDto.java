package health.camp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerDto {
    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
}
