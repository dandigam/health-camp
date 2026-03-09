package health.camp.entity;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@jakarta.persistence.Table(name = "documents")
@Getter
@Setter
public class Document {
    @Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private String documentName;
    private String fileType;
    private String fileUrl;
    private String module; // STOCK_INVOICE, PATIENT_DOC etc
    private LocalDateTime uploadedAt;
}
