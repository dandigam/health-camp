package health.camp.repository;

import health.camp.model.Camp;
import health.camp.model.enums.CampStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampRepository extends MongoRepository<Camp, Long> {

    @Query("""
            {
              $or: [
                { "name": { $regex: ?0, $options: "i" } },
                { "village": { $regex: ?0, $options: "i" } },
                { "district": { $regex: ?0, $options: "i" } }
              ]
            }
            """)
    List<Camp> findBySearch(String search);

}
