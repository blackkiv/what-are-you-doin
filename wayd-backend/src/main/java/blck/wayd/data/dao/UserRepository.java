package blck.wayd.data.dao;

import blck.wayd.data.entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * User Repository.
 */
public interface UserRepository extends ReactiveCrudRepository<User, UUID> {

    Mono<User> findByUsernameAndPassword(String username, String password);

    Mono<User> findByToken(UUID token);

    Mono<Boolean> existsByUsername(String username);

    Mono<Boolean> existsByToken(UUID token);
}
