package health.camp.repository;

import health.camp.entity.SupplierRequestItem;
import health.camp.entity.SupplierRequest;
import health.camp.entity.MedicineLookup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SupplierRequestItemRepository extends JpaRepository<SupplierRequestItem, Long> {
    List<SupplierRequestItem> findByRequest(SupplierRequest request);
    Optional<SupplierRequestItem> findByRequestAndMedicine(SupplierRequest request, MedicineLookup medicine);
}
