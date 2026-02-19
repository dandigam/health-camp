package health.camp.repository;

import health.camp.entity.MedicineLookup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicineRepository extends JpaRepository<MedicineLookup, Long> {

    Optional<MedicineLookup> findByName(String name);
}

