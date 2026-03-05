
package health.camp.repository;

import health.camp.entity.WarehouseInventory;
import health.camp.entity.WareHouse;
import health.camp.entity.MedicineLookup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
public interface WarehouseInventoryRepository extends JpaRepository<WarehouseInventory, Long> {

     
    List<WarehouseInventory> findByWarehouse(WareHouse warehouse);
    Optional<WarehouseInventory> findByWarehouseAndMedicine(WareHouse warehouse, MedicineLookup medicine);
    List<WarehouseInventory> findByWarehouseAndTotalQtyLessThanEqual(WareHouse warehouse, Integer qty);
    List<WarehouseInventory> findByWarehouseAndTotalQtyGreaterThan(WareHouse warehouse, Integer qty);
   
    @Query("SELECT COUNT(DISTINCT wi.medicine) FROM WarehouseInventory wi WHERE wi.warehouse.id = ?1")
    long findByWarehouseIdDistinctMedicineCount(Long warehouseId);

    @Query("SELECT COUNT(wi) FROM WarehouseInventory wi WHERE wi.warehouse.id = ?1 AND wi.totalQty < 30")
    int countLowStockMedicinesByWarehouseId(Long warehouseId);
}
