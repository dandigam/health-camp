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
        public boolean deleteDocumentById(Long id) {
            var documentOpt = documentRepository.findById(id);
            if (documentOpt.isPresent()) {
                Document document = documentOpt.get();
                // Delete file from storage
                try {
                    java.io.File file = new java.io.File(document.getFileUrl());
                    if (file.exists()) {
                        file.delete();
                    }
                } catch (Exception e) {
                    // Log error if needed
                }
                documentRepository.deleteById(id);
                return true;
            }
            return false;
        }
    private final DocumentRepository documentRepository;

    public DocumentUploadResponse uploadFile(MultipartFile file) {
            String originalFileName = file.getOriginalFilename();
            String timestamp = java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
            String fileName = timestamp + "_" + originalFileName;
            String storagePath = "E:/FileStorage/" + fileName;
            try {
                file.transferTo(new java.io.File(storagePath));
            } catch (Exception e) {
                throw new RuntimeException("Failed to store file", e);
            }

            Document document = new Document();
            document.setDocumentName(fileName);
            document.setFileType(file.getContentType());
            document.setFileUrl(storagePath);
            document.setModule("STOCK_INVOICE");
            document.setUploadedAt(LocalDateTime.now());

            document = documentRepository.save(document);

            return new DocumentUploadResponse(
                    document.getId(),
                    document.getDocumentName(),
                    document.getFileUrl()
            );
        }
    
        public Document getDocumentById(Long id) {
            return documentRepository.findById(id).orElse(null);
        }
}
