package health.camp.repository;

import health.camp.entity.WareHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WareHouseRepository extends JpaRepository<WareHouse, Long> {
    Optional<WareHouse> findByUserId(Long id);

}
