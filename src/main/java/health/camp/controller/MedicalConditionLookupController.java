package health.camp.controller;

import health.camp.entity.MedicalConditionLookup;
import health.camp.service.MedicalConditionLookupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/medical-conditions")
@RequiredArgsConstructor
@CrossOrigin("*")  // Allow CORS for all origins (adjust as needed)
public class MedicalConditionLookupController {
    private final MedicalConditionLookupService service;

    @GetMapping
    public ResponseEntity<List<MedicalConditionLookup>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
