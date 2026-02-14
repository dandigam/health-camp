package health.camp.repository;

import health.camp.model.Prescription;
import health.camp.model.enums.PrescriptionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository extends MongoRepository<Prescription, String> {

    Page<Prescription> findByCampId(String campId, Pageable pageable);

    Page<Prescription> findByPatientId(String patientId, Pageable pageable);

    Page<Prescription> findByCampIdAndStatus(String campId, PrescriptionStatus status, Pageable pageable);
}
