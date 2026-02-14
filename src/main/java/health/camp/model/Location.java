package health.camp.model;

import lombok.Data;
@Data
public class Location {

    private String state;
    private String district;
    private String mandal;
    private String village;
    private String address;
    private String pinCode;
}
