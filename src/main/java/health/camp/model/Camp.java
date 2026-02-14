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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collection = "camps")
@Data
public class Camp extends Location {

    @Id
    private Long id;
    private String name;
    private String organizerName;
    private String organizerPhone;
    private String organizerEmail;

    @CreatedBy
    private String createdBy;
    @CreatedDate
    private LocalDateTime createdAt;

}
