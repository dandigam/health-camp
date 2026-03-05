
package health.camp.repository;

import health.camp.entity.Supplier;
import health.camp.entity.WareHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import health.camp.model.enums.Status;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    List<Supplier> findByWarehouse(WareHouse warehouse);

    @Query("SELECT COUNT(s) FROM Supplier s WHERE s.warehouse.id = ?1 AND s.status = ?2")
    int countByWarehouseIdAndStatus(Long warehouseId, Status status);
}
