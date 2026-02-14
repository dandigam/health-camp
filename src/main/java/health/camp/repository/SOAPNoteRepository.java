package health.camp.repository;

import health.camp.model.SOAPNote;
import health.camp.model.enums.SOAPNoteStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SOAPNoteRepository extends MongoRepository<SOAPNote, String> {

    Page<SOAPNote> findByCampId(String campId, Pageable pageable);

    Page<SOAPNote> findByPatientId(String patientId, Pageable pageable);

    Page<SOAPNote> findByCampIdAndStatus(String campId, SOAPNoteStatus status, Pageable pageable);

    Page<SOAPNote> findByCampIdAndPatientId(String campId, String patientId, Pageable pageable);
}
