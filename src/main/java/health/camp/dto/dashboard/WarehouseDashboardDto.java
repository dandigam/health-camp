package health.camp.dto.dashboard;

import lombok.Data;
import java.util.Map;

@Data
public class WarehouseDashboardDto {
    private int totalMedicines;
    private int lowStock;
    private int suppliers;
    private Map<String, Long> supplierOrders;
}
