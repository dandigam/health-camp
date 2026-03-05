package health.camp.repository;

import health.camp.entity.UserCampEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCampEventRepository extends JpaRepository<UserCampEvent, Long> {

    Optional<UserCampEvent> findByUserIdAndStatus(Long userId, String status);

}
