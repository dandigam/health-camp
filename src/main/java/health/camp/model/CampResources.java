package health.camp.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Document(collection = "campResources")
@Data
public class CampResources {

    @Id
    private Long id;

    @Indexed
    private Long campId;

    private List<Long> doctorIds = new ArrayList<>();
    private List<Long> pharmacyIds = new ArrayList<>();
    private List<Long> staffIds = new ArrayList<>();

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdAt;
}
