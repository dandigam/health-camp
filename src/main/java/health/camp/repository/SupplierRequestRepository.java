package health.camp.repository;

import health.camp.entity.SupplierRequest;
import health.camp.entity.WareHouse;
import health.camp.model.enums.Status;
import health.camp.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SupplierRequestRepository extends JpaRepository<SupplierRequest, Long> {
    List<SupplierRequest> findByWarehouse(WareHouse warehouse);
    List<SupplierRequest> findBySupplier(Supplier supplier);
    List<SupplierRequest> findByWarehouseAndSupplier(WareHouse warehouse, Supplier supplier);
    List<SupplierRequest> findByWarehouseAndStatus(WareHouse warehouse, Status status);

@Query("""
       SELECT s.status, COUNT(s)
       FROM SupplierRequest s
       WHERE s.warehouse.id = :warehouseId
       GROUP BY s.status
       """)
List<Object[]> countByWarehouseIdGroupByStatu(@Param("warehouseId") Long warehouseId);
}
