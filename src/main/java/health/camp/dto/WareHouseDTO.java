package health.camp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WareHouseDTO {


    private Long id;

    private String name;
    private String email;
    private String phoneNumber;
    private String authorizedPerson;
    private String licenceNumber;

    private String state;
    private String district;
    private String mandal;
    private String village;
    private String pinCode;

    private Long userId;
}
