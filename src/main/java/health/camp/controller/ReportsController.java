package health.camp.controller;

import health.camp.dto.reports.ReportsOverviewResponse;
import health.camp.service.ReportsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Reports", description = "Reports APIs")
@RestController
@RequestMapping("/api/reports")
public class ReportsController {

    private final ReportsService reportsService;

    public ReportsController(ReportsService reportsService) {
        this.reportsService = reportsService;
    }

    @Operation(summary = "Reports overview")
    @GetMapping("/overview")
    public ResponseEntity<ReportsOverviewResponse> getOverview(@RequestParam(required = false) String campId) {
        return ResponseEntity.ok(reportsService.getOverview(campId));
    }
}
