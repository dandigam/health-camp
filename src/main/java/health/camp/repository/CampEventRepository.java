package health.camp.repository;

import health.camp.entity.CampEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampEventRepository extends JpaRepository<CampEvent, Long> {
}
