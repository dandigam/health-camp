package health.camp.repository;

import health.camp.model.CampStart;
import health.camp.model.enums.CampStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CampStartRepository extends MongoRepository<CampStart, Long> {

    // Find all instances of a camp (Jan, Mar, Jun runs)
    List<CampStart> findByCampId(Long campId);

    Optional<CampStart> findTopByCampIdOrderByPlanDateDesc(Long campId);

}
