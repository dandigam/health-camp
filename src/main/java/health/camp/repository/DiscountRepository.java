package health.camp.repository;

import health.camp.model.Discount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends MongoRepository<Discount, String> {

    Page<Discount> findByCampId(String campId, Pageable pageable);

    Page<Discount> findByPatientId(String patientId, Pageable pageable);
}
