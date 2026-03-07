package health.camp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DocumentUploadResponse {
    private Long documentId;
    private String documentName;
    private String documentUrl;
}
