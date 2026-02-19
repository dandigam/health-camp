package health.camp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import health.camp.entity.State;

public interface StateRepository extends JpaRepository<State, Long> {
}
