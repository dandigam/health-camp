package health.camp.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collection = "doctors")
@Data
public class Doctor {
   @Id
    private Long id;
    private String name;
    private String specialization;
    private String phone;
    private String email;
    private String avatar;
    private String photoUrl;
    private boolean active = true;
    private List<String> campIds = new ArrayList<>();

}
