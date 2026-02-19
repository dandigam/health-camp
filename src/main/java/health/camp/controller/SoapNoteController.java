package health.camp.controller;

import health.camp.dto.soap.SOAPNoteRequest;
import health.camp.dto.soap.SOAPNoteResponse;
import health.camp.service.SoapNoteService;
import health.camp.security.AppUserDetailsService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "SOAP Notes", description = "SOAP notes APIs")
@RestController
@RequestMapping("/api/soap-notes")
@Hidden
public class SoapNoteController {

    private final SoapNoteService soapNoteService;
    private final AppUserDetailsService userDetailsService;

    public SoapNoteController(SoapNoteService soapNoteService, AppUserDetailsService userDetailsService) {
        this.soapNoteService = soapNoteService;
        this.userDetailsService = userDetailsService;
    }

    @Operation(summary = "List SOAP notes")
    @GetMapping
    public ResponseEntity<Page<SOAPNoteResponse>> list(
            @RequestParam(required = false) String campId,
            @RequestParam(required = false) String patientId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String search,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(soapNoteService.list(campId, patientId, status, search, pageable));
    }

    @Operation(summary = "Get SOAP note by ID")
    @GetMapping("/{id}")
    public ResponseEntity<SOAPNoteResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(soapNoteService.getById(id));
    }

    @Operation(summary = "Create SOAP note")
    @PostMapping
    public ResponseEntity<SOAPNoteResponse> create(@Valid @RequestBody SOAPNoteRequest request) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.status(201).body(soapNoteService.create(request, userId));
    }

    @Operation(summary = "Update SOAP note")
    @PatchMapping("/{id}")
    public ResponseEntity<SOAPNoteResponse> update(@PathVariable String id, @RequestBody SOAPNoteRequest request) {
        return ResponseEntity.ok(soapNoteService.update(id, request));
    }
}
