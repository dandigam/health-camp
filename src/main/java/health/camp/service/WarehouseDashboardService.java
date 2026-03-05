package health.camp.service;

import health.camp.dto.dashboard.WarehouseDashboardDto;
import health.camp.model.enums.Status;
import health.camp.repository.SupplierRepository;
import health.camp.repository.SupplierRequestRepository;
import health.camp.repository.WarehouseInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WarehouseDashboardService {

    private final WarehouseInventoryRepository warehouseInventoryRepository;
    private final SupplierRepository supplierRepository;
    private final SupplierRequestRepository supplierRequestRepository;

    public WarehouseDashboardDto getDashboard(Long warehouseId) {

        WarehouseDashboardDto dto = new WarehouseDashboardDto();

        dto.setTotalMedicines(
                (int) warehouseInventoryRepository
                        .findByWarehouseIdDistinctMedicineCount(warehouseId)
        );

        dto.setLowStock(
                warehouseInventoryRepository
                        .countLowStockMedicinesByWarehouseId(warehouseId)
        );

        dto.setSuppliers(
                supplierRepository
                        .countByWarehouseIdAndStatus(warehouseId, Status.ACTIVE)
        );

    
List<Object[]> results = supplierRequestRepository.countByWarehouseIdGroupByStatu(warehouseId);

long pending = 0;
long received = 0;
long cancelled = 0;

for (Object[] row : results) {
    Status status = (Status) row[0];
    Long count = (Long) row[1];

    switch (status) {
        case PENDING -> pending = count;
        case RECEIVED -> received = count;
        case CANCELLED -> cancelled = count;
    }
}

       // long pending = orderCounts.getOrDefault(Status.PENDING, 0L);
        //long received = orderCounts.getOrDefault(Status.RECEIVED, 0L);
        //long cancelled = orderCounts.getOrDefault(Status.CANCELLED, 0L);

        dto.setSupplierOrders(
                Map.of(
                        "pendingOrders", pending,
                        "received", received,
                        "cancelled", cancelled,
                        "totalOrders", pending + received + cancelled
                )
        );

        return dto;
    }
}