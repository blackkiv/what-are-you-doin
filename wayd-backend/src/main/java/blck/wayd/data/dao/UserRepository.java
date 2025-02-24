package blck.wayd.data.dao;

import blck.wayd.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * User Repository.
 */
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsernameAndPassword(String username, String password);

    Optional<User> findByToken(UUID token);

    Boolean existsByUsername(String username);

    Boolean existsByToken(UUID token);
}
