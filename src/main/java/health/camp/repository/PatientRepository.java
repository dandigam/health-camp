package health.camp.repository;

import health.camp.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends MongoRepository<Patient, String> {

    long countByCampId(Long campId);

    boolean existsByCampIdAndPatientId(String campId, String patientId);

    Page<Patient> findByCampId(String campId, Pageable pageable);

    @Query("{ 'campId': ?0, $or: [ { 'patientId': { $regex: ?1, $options: 'i' } }, { 'name': { $regex: ?1, $options: 'i' } }, { 'surname': { $regex: ?1, $options: 'i' } } ] }")
    Page<Patient> findByCampIdAndSearch(String campId, String search, Pageable pageable);

    @Query("{ $or: [ { 'patientId': { $regex: ?0, $options: 'i' } }, { 'name': { $regex: ?0, $options: 'i' } }, { 'surname': { $regex: ?0, $options: 'i' } } ] }")
    Page<Patient> findBySearch(String search, Pageable pageable);
}
