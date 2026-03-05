package health.camp.repository;

import health.camp.entity.EncounterSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EncounterSubjectRepository extends JpaRepository<EncounterSubject, Long> {

    Optional<EncounterSubject> findByEncounterId(Long encounterId);
}
