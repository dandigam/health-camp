package health.camp.service;

import health.camp.dto.stock.InvoiceDocumentDto;
import health.camp.entity.Invoice;
import health.camp.entity.InvoiceDocument;
import health.camp.repository.InvoiceDocumentRepository;
import health.camp.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceDocumentService {

    private final InvoiceDocumentRepository invoiceDocumentRepository;
    private final InvoiceRepository invoiceRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    /**
     * Upload one or multiple invoice documents (PDF, images) for an invoice
     */
    @Transactional
    public List<InvoiceDocumentDto> uploadDocuments(Long invoiceId, MultipartFile[] files) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + invoiceId));

        Path invoiceDir = Paths.get(uploadDir, "invoices", String.valueOf(invoiceId));
        try {
            Files.createDirectories(invoiceDir);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory", e);
        }

        List<InvoiceDocumentDto> result = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }

            validateFile(file);

            // Generate unique file name to avoid collisions
            String originalFileName = file.getOriginalFilename();
            String extension = "";
            if (originalFileName != null && originalFileName.contains(".")) {
                extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }
            String storedFileName = UUID.randomUUID().toString() + extension;

            Path targetPath = invoiceDir.resolve(storedFileName);
            try {
                Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException("Could not store file: " + originalFileName, e);
            }

            InvoiceDocument document = InvoiceDocument.builder()
                    .invoice(invoice)
                    .fileName(storedFileName)
                    .originalFileName(originalFileName != null ? originalFileName : storedFileName)
                    .contentType(file.getContentType())
                    .fileSize(file.getSize())
                    .filePath(targetPath.toString().replace("\\", "/"))
                    .build();

            InvoiceDocument saved = invoiceDocumentRepository.save(document);
            result.add(mapToDto(saved));
        }

        return result;
    }

    /**
     * Get all documents for an invoice
     */
    public List<InvoiceDocumentDto> getDocumentsByInvoiceId(Long invoiceId) {
        return invoiceDocumentRepository.findByInvoiceId(invoiceId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Get a single document by ID
     */
    public InvoiceDocumentDto getDocumentById(Long documentId) {
        InvoiceDocument document = invoiceDocumentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + documentId));
        return mapToDto(document);
    }

    /**
     * Load file as Resource for download
     */
    public Resource loadFileAsResource(Long documentId) {
        InvoiceDocument document = invoiceDocumentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + documentId));

        try {
            Path filePath = Paths.get(document.getFilePath());
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("File not found or not readable: " + document.getOriginalFileName());
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("File not found: " + document.getOriginalFileName(), e);
        }
    }

    /**
     * Get original filename for Content-Disposition header
     */
    public String getOriginalFileName(Long documentId) {
        InvoiceDocument document = invoiceDocumentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + documentId));
        return document.getOriginalFileName();
    }

    /**
     * Get content type for response header
     */
    public String getContentType(Long documentId) {
        InvoiceDocument document = invoiceDocumentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + documentId));
        return document.getContentType();
    }

    /**
     * Delete a specific document
     */
    @Transactional
    public void deleteDocument(Long documentId) {
        InvoiceDocument document = invoiceDocumentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + documentId));

        // Delete physical file
        try {
            Path filePath = Paths.get(document.getFilePath());
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            // Log warning but don't fail the delete
        }

        invoiceDocumentRepository.delete(document);
    }

    /**
     * Delete all documents for an invoice
     */
    @Transactional
    public void deleteAllDocumentsByInvoiceId(Long invoiceId) {
        List<InvoiceDocument> documents = invoiceDocumentRepository.findByInvoiceId(invoiceId);
        for (InvoiceDocument doc : documents) {
            try {
                Path filePath = Paths.get(doc.getFilePath());
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                // Log warning but don't fail
            }
        }
        invoiceDocumentRepository.deleteByInvoiceId(invoiceId);
    }

    /**
     * Validate uploaded file type (PDF, images only)
     */
    private void validateFile(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null) {
            throw new RuntimeException("File type could not be determined");
        }

        boolean isValid = contentType.equals("application/pdf")
                || contentType.startsWith("image/");

        if (!isValid) {
            throw new RuntimeException("Invalid file type: " + contentType
                    + ". Only PDF and image files (JPEG, PNG, etc.) are allowed.");
        }

        // Max 10MB per file
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new RuntimeException("File size exceeds maximum limit of 10MB");
        }
    }

    public InvoiceDocumentDto mapToDto(InvoiceDocument document) {
        return InvoiceDocumentDto.builder()
                .id(document.getId())
                .invoiceId(document.getInvoice().getId())
                .fileName(document.getFileName())
                .originalFileName(document.getOriginalFileName())
                .contentType(document.getContentType())
                .fileSize(document.getFileSize())
                .filePath(document.getFilePath())
                .downloadUrl("/api/invoices/documents/" + document.getId() + "/download")
                .createdAt(document.getCreatedAt() != null ? document.getCreatedAt().format(DATE_FORMATTER) : null)
                .createdBy(document.getCreatedBy())
                .build();
    }
}

