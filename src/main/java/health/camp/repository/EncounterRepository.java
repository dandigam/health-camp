package health.camp.repository;

import health.camp.entity.Encounter;
import health.camp.model.enums.EncounterStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EncounterRepository extends JpaRepository<Encounter, Long> {

    // Find all encounters by campEventId
    List<Encounter> findByCampEventId(Long campEventId);

}
