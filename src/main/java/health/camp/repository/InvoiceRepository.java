package health.camp.repository;

import health.camp.entity.Invoice;
import health.camp.entity.Supplier;
import health.camp.entity.WareHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findByWarehouse(WareHouse warehouse);

    List<Invoice> findBySupplier(Supplier supplier);

    List<Invoice> findByWarehouseId(Long warehouseId);

    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);

    @Query("SELECT i FROM Invoice i WHERE i.warehouse.id = ?1 AND i.invoiceDate BETWEEN ?2 AND ?3")
    List<Invoice> findByWarehouseIdAndDateRange(Long warehouseId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT i FROM Invoice i WHERE i.warehouse.id = ?1 AND i.supplier.supplierId = ?2")
    List<Invoice> findByWarehouseIdAndSupplierId(Long warehouseId, Long supplierId);

    @Query("SELECT COUNT(i) FROM Invoice i WHERE i.warehouse.id = ?1")
    long countByWarehouseId(Long warehouseId);
}
