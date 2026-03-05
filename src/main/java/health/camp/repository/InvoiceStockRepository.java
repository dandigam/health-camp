package health.camp.repository;

import health.camp.entity.Invoice;
import health.camp.entity.InvoiceStock;
import health.camp.entity.MedicineLookup;
import health.camp.entity.WareHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceStockRepository extends JpaRepository<InvoiceStock, Long> {

    List<InvoiceStock> findByInvoice(Invoice invoice);

    List<InvoiceStock> findByInvoiceId(Long invoiceId);

    List<InvoiceStock> findByWarehouse(WareHouse warehouse);

    List<InvoiceStock> findByWarehouseId(Long warehouseId);

    List<InvoiceStock> findByMedicine(MedicineLookup medicine);

    @Query("SELECT s FROM InvoiceStock s WHERE s.warehouse.id = ?1 AND s.medicine.medicineId = ?2")
    List<InvoiceStock> findByWarehouseIdAndMedicineId(Long warehouseId, Long medicineId);

    @Query("SELECT s FROM InvoiceStock s WHERE s.warehouse.id = ?1 AND s.expDate < ?2")
    List<InvoiceStock> findExpiredStockByWarehouseId(Long warehouseId, LocalDate currentDate);

    @Query("SELECT s FROM InvoiceStock s WHERE s.warehouse.id = ?1 AND s.expDate BETWEEN ?2 AND ?3")
    List<InvoiceStock> findExpiringStockByWarehouseId(Long warehouseId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT SUM(s.quantity) FROM InvoiceStock s WHERE s.warehouse.id = ?1 AND s.medicine.medicineId = ?2")
    Long getTotalQuantityByWarehouseAndMedicine(Long warehouseId, Long medicineId);

    @Query("SELECT s FROM InvoiceStock s WHERE s.batchNo = ?1")
    List<InvoiceStock> findByBatchNo(String batchNo);
}
