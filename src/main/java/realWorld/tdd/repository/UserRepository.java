package realWorld.tdd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import realWorld.tdd.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
