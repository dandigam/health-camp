package health.camp.repository;

import health.camp.model.Consultation;
import health.camp.model.enums.ConsultationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultationRepository extends MongoRepository<Consultation, String> {

    Page<Consultation> findByCampId(String campId, Pageable pageable);

    Page<Consultation> findByPatientId(String patientId, Pageable pageable);

    Page<Consultation> findByDoctorId(String doctorId, Pageable pageable);

    Page<Consultation> findByCampIdAndStatus(String campId, ConsultationStatus status, Pageable pageable);
}
