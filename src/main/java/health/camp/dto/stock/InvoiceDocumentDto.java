package health.camp.dto.stock;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceDocumentDto {

    private Long id;
    private Long invoiceId;
    private String fileName;
    private String originalFileName;
    private String contentType;
    private Long fileSize;
    private String filePath;
    private String downloadUrl;
    private String createdAt;
    private String createdBy;
}

