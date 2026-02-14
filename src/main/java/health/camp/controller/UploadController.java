package health.camp.controller;

import health.camp.dto.upload.UploadResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Placeholder for file upload. In production, integrate with S3/MinIO and return presigned or stored URL.
 */
@Tag(name = "Upload", description = "File upload APIs")
@RestController
@RequestMapping("/api")
public class UploadController {

    @Operation(summary = "Upload photo")
    @PostMapping("/upload")
    public ResponseEntity<UploadResponse> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(required = false) String type) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(new UploadResponse(null));
        }
        String url = "https://storage.example.com/" + (type != null ? type + "/" : "") + file.getOriginalFilename();
        return ResponseEntity.ok(new UploadResponse(url));
    }
}
