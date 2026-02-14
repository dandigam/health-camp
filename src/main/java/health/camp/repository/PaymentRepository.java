package health.camp.repository;

import health.camp.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface PaymentRepository extends MongoRepository<Payment, String> {

    Page<Payment> findByCampId(Long campId, Pageable pageable);

    Page<Payment> findByPatientId(String patientId, Pageable pageable);

    Page<Payment> findByPrescriptionId(String prescriptionId, Pageable pageable);
}
