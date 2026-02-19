package health.camp.repository;

import health.camp.entity.WarehouseSupplierMedicine;
import health.camp.entity.Supplier;
import health.camp.entity.WareHouse;
import health.camp.entity.MedicineLookup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WarehouseSupplierMedicineRepository extends JpaRepository<WarehouseSupplierMedicine, Long> {
    List<WarehouseSupplierMedicine> findBySupplier(Supplier supplier);
    
    List<WarehouseSupplierMedicine> findByWarehouseAndSupplier(WareHouse warehouse, Supplier supplier);
    
    Optional<WarehouseSupplierMedicine> findByWarehouseAndSupplierAndMedicine(
            WareHouse warehouse, Supplier supplier, MedicineLookup medicine);
}
