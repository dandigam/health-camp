package health.camp.repository;

import health.camp.entity.SupplierRequest;
import health.camp.entity.WareHouse;
import health.camp.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierRequestRepository extends JpaRepository<SupplierRequest, Long> {
    List<SupplierRequest> findByWarehouse(WareHouse warehouse);
    List<SupplierRequest> findBySupplier(Supplier supplier);
    List<SupplierRequest> findByWarehouseAndSupplier(WareHouse warehouse, Supplier supplier);
    List<SupplierRequest> findByWarehouseAndStatus(WareHouse warehouse, SupplierRequest.Status status);
}
