package health.camp.model;

import health.camp.model.enums.CampStatus;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Document(collection = "campStart")
public class CampStart {

    @Id
    private Long id;

    @Indexed
    private Long campId;

    @Indexed
    private LocalDate planDate;

    @Indexed
    private CampStatus campStatus;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdAt;
}
