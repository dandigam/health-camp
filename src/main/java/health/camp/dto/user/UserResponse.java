package health.camp.dto.user;

import health.camp.dto.WareHouseDTO;
import health.camp.model.enums.UserRole;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private UserRole role;
    private String avatar;
    private String roleDisplayName;

    private List<String> permissions;

    private UserContextDTO context;

}
