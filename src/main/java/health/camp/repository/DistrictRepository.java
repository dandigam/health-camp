package health.camp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import health.camp.entity.District;

public interface DistrictRepository extends JpaRepository<District, Long> {
}
