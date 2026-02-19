package health.camp.repository;

import health.camp.entity.WarehouseInventory;
import health.camp.entity.WareHouse;
import health.camp.entity.MedicineLookup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WarehouseInventoryRepository extends JpaRepository<WarehouseInventory, Long> {
    
    List<WarehouseInventory> findByWarehouse(WareHouse warehouse);
    
    Optional<WarehouseInventory> findByWarehouseAndMedicine(WareHouse warehouse, MedicineLookup medicine);
    
    List<WarehouseInventory> findByWarehouseAndTotalQtyLessThanEqual(WareHouse warehouse, Integer qty);
    
    List<WarehouseInventory> findByWarehouseAndTotalQtyGreaterThan(WareHouse warehouse, Integer qty);
}
