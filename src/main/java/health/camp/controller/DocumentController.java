package health.camp.controller;

import health.camp.dto.DocumentUploadResponse;
import health.camp.service.DocumentService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.File;
import java.nio.file.Files;

import org.springframework.core.io.FileSystemResource;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DocumentController {
        @DeleteMapping("/{id}")
        public ResponseEntity<?> deleteDocumentById(@PathVariable Long id) {
            boolean deleted = documentService.deleteDocumentById(id);
            if (deleted) {
                return ResponseEntity.ok("Document deleted successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        }
    private final DocumentService documentService;

@GetMapping("/download/{id}")
public ResponseEntity<?> downloadDocument(@PathVariable Long id) {
    var document = documentService.getDocumentById(id);
    if (document == null) {
        return ResponseEntity.notFound().build();
    }
    try {
        File file = new File(document.getFileUrl());
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new FileSystemResource(file);
        String filename = document.getDocumentName();

        // Set disposition: inline for PDF, attachment for others
        String disposition;
        if (filename.toLowerCase().endsWith(".pdf")) {
            disposition = "inline"; // preview in browser
        } else {
            disposition = "attachment"; // force download
        }

        // Optionally set content type for better browser support
        String contentType = filename.toLowerCase().endsWith(".pdf") ? "application/pdf" : "application/octet-stream";

        return ResponseEntity.ok()
                .header("Content-Disposition", disposition + "; filename=\"" + filename + "\"")
                .header("Content-Type", contentType)
                .body(resource);

    } catch (Exception e) {
        return ResponseEntity.status(500).body("Error downloading file");
    }
}
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public DocumentUploadResponse uploadFile(@RequestParam("file") MultipartFile file) {
        return documentService.uploadFile(file);
    }

@GetMapping("/preview/{id}")
public ResponseEntity<Resource> previewDocument(@PathVariable Long id) throws Exception {
    var document = documentService.getDocumentById(id);
    if (document == null) return ResponseEntity.notFound().build();

    File file = new File(document.getFileUrl());
    if (!file.exists()) return ResponseEntity.notFound().build();

    Resource resource = new FileSystemResource(file);
    String filename = document.getDocumentName();
    String contentType = filename.toLowerCase().endsWith(".pdf") ? "application/pdf" : Files.probeContentType(file.toPath());

    return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header("Content-Disposition", "inline; filename=\"" + filename + "\"")
            .header("X-Frame-Options", "SAMEORIGIN")
            .body(resource);
}

}
    


