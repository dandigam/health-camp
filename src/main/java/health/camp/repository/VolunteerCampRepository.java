package health.camp.repository;

import health.camp.entity.VolunteerCamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolunteerCampRepository extends JpaRepository<VolunteerCamp, Long> {
}
