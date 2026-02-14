package health.camp.repository;

import health.camp.model.StockItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StockItemRepository extends MongoRepository<StockItem, String> {

    Page<StockItem> findByCampId(String campId, Pageable pageable);

    Page<StockItem> findByMedicineId(String medicineId, Pageable pageable);

    Page<StockItem> findByCampIdAndMedicineId(String campId, String medicineId, Pageable pageable);

    @Query("{ 'campId': ?0, 'quantity': { $lt: ?1 } }")
    Page<StockItem> findByCampIdAndQuantityLessThan(String campId, int threshold, Pageable pageable);
}
