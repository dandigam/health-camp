package health.camp.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "counters")
public class Counter {

    @Id
    private String id;   // e.g. "doctor_seq"
    private long seq;    // 1, 2, 3...
}
