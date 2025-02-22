package blck.wayd.service;

import blck.wayd.data.dao.UserRepository;
import blck.wayd.data.entity.User;
import blck.wayd.exceptions.UserAlreadyExists;
import blck.wayd.exceptions.UserNotExists;
import blck.wayd.model.dto.UserDto;
import blck.wayd.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * User Service.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public Mono<UUID> getUserTokenByCredentials(String username, String password) {
        return repository.findByUsernameAndPassword(username, PasswordUtil.hashPassword(password))
                .switchIfEmpty(Mono.error(new UserNotExists(username)))
                .map(User::getToken);
    }

    public Mono<UUID> createUser(UserDto createUserDto, String rawPassword) {
        var username = createUserDto.getUsername();
        return repository.existsByUsername(username)
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new UserAlreadyExists(username));
                    } else {
                        var user = new User(
                                username,
                                PasswordUtil.hashPassword(rawPassword),
                                UUID.randomUUID()
                        );
                        return repository.save(user)
                                .map(User::getToken);
                    }
                });
    }

    public <T> Flux<T> validateUserExistsByToken(UUID token, Flux<T> publisher) {
        return repository.existsByToken(token)
                .flux()
                .flatMap(exists -> {
                    if (exists) {
                        return publisher;
                    } else {
                        return Flux.error(new UserNotExists(token));
                    }
                });
    }

    public <T> Mono<T> validateUserExistsByToken(UUID token, Mono<T> publisher) {
        return repository.existsByToken(token)
                .flatMap(exists -> {
                    if (exists) {
                        return publisher;
                    } else {
                        return Mono.error(new UserNotExists(token));
                    }
                });
    }
}
