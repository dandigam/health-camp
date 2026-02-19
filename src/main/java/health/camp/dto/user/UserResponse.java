package health.camp.dto.user;

import health.camp.dto.WareHouseDTO;
import health.camp.model.enums.UserRole;
import lombok.Data;

import java.security.Permissions;
import java.util.List;

@Data
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private UserRole role;
    private String avatar;

    private WareHouseDTO wareHouse;
    private List<String> permissions;


}
