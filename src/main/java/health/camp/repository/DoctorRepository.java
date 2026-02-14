package health.camp.repository;

import health.camp.model.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends MongoRepository<Doctor, Long> {

    List<Doctor> findByCampIdsContaining(String campId);

    @Query("{ 'campIds': ?0 }")
    Doctor findByCampId(String campId);

    @Query("{ 'active': ?0 }")
    List<Doctor> findByActive(boolean active);

    @Query("{ $or: [ { 'name': { $regex: ?0, $options: 'i' } }, { 'specialization': { $regex: ?0, $options: 'i' } } ] }")
    List<Doctor> findBySearch(String search);

    @Query("{ 'campIds': ?0, $or: [ { 'name': { $regex: ?1, $options: 'i' } }, { 'specialization': { $regex: ?1, $options: 'i' } } ] }")
    List<Doctor> findByCampIdAndSearch(String campId, String search);
}
