package health.camp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import health.camp.entity.Mandal;

public interface MandalRepository extends JpaRepository<Mandal, Long> {
}
