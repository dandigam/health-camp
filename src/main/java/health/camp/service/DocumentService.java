package health.camp.service;

import health.camp.entity.Document;
import health.camp.repository.DocumentRepository;
import health.camp.dto.DocumentUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;

    public DocumentUploadResponse uploadFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        // save file to storage (local / s3 / server folder)
        String fileUrl = "/uploads/" + fileName;

        Document document = new Document();
        document.setDocumentName(fileName);
        document.setFileType(file.getContentType());
        document.setFileUrl(fileUrl);
        document.setModule("STOCK_INVOICE");
        document.setUploadedAt(LocalDateTime.now());

        document = documentRepository.save(document);

        return new DocumentUploadResponse(
                document.getId(),
                document.getDocumentName(),
                document.getFileUrl()
        );
    }
}
