package health.camp.repository;

import health.camp.entity.DoctorCamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorCampRepository extends JpaRepository<DoctorCamp, Long> {
}
