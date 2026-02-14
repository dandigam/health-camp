package health.camp.repository;

import health.camp.model.CampResources;
import health.camp.model.CampStart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CampResourcesRepository extends MongoRepository<CampResources, Long> {

    List<CampResources> findByCampId(Long campId);
    Optional<CampResources> findTopByCampIdOrderByCreatedAtDesc(Long campId);

}
