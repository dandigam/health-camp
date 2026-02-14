package health.camp.dto.doctor;

import lombok.Data;

import java.util.List;
@Data
public class DoctorResponse {

    private Long id;
    private String name;
    private String specialization;
    private String phone;
    private String email;
    private String avatar;
    private String photoUrl;
    private boolean active;
    private List<String> campIds;

}
