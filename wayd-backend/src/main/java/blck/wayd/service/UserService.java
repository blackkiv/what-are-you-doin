package blck.wayd.service;

import blck.wayd.data.dao.UserRepository;
import blck.wayd.data.entity.User;
import blck.wayd.exceptions.NoData;
import blck.wayd.exceptions.UserAlreadyExists;
import blck.wayd.exceptions.UserNotExists;
import blck.wayd.model.dto.UserDto;
import blck.wayd.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

/**
 * User Service.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public UUID getUserTokenByCredentials(String username, String password) {
        return repository.findByUsernameAndPassword(username, PasswordUtil.hashPassword(password))
                .orElseThrow(() -> new UserNotExists(username))
                .getToken();
    }

    public UUID createUser(UserDto createUserDto, String rawPassword) {
        var username = createUserDto.getUsername();
        var userExists = repository.existsByUsername(username);
        if (userExists) {
            throw new UserAlreadyExists(username);
        } else {
            var user = new User(
                    username,
                    PasswordUtil.hashPassword(rawPassword),
                    UUID.randomUUID()
            );
            var savedUser = repository.save(user);
            return savedUser.getToken();
        }
    }

    public <T> List<T> validateUserExistsByTokenAndPropagateUserId(
            UUID token,
            Function<UUID, List<T>> publisherFunction) {

        var user = repository.findByToken(token)
                .orElseThrow(() -> new UserNotExists(token));
        var result = publisherFunction.apply(user.getId());
        if (result.isEmpty()) {
            throw new NoData();
        }
        return result;
    }
}
