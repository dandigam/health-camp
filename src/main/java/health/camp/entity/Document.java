package health.camp.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
@Getter
@Setter
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String documentName;
    private String fileType;
    private String fileUrl;
    private String module; // STOCK_INVOICE, PATIENT_DOC etc
    private LocalDateTime uploadedAt;
}
