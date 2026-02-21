package health.camp.repository;

import health.camp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {


    Optional<User> findByUserName(String userName);

    Optional<User> findByPhone(String phone);

    boolean existsByEmail(String email);
}
