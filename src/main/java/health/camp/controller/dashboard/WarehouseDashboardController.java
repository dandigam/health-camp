package health.camp.controller.dashboard;


import health.camp.dto.dashboard.WarehouseDashboardDto;
import health.camp.service.WarehouseDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard/warehouse")
@RequiredArgsConstructor
@CrossOrigin("*")
public class WarehouseDashboardController {

    private final WarehouseDashboardService warehouseDashboardService;

    @GetMapping("/{warehouseId}")
    public WarehouseDashboardDto getWarehouseDashboard(
            @PathVariable Long warehouseId) {

        return warehouseDashboardService.getDashboard(warehouseId);
    }
}
