package health.camp.repository;

import health.camp.entity.MedicalConditionLookup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalConditionLookupRepository extends JpaRepository<MedicalConditionLookup, Long> {

    List<MedicalConditionLookup> findByIsActiveTrueOrderByDisplayOrderAsc();
}
