package blck.wayd.service;

import blck.wayd.data.dao.UserRepository;
import blck.wayd.data.entity.User;
import blck.wayd.exceptions.UserAlreadyExists;
import blck.wayd.exceptions.UserNotExists;
import blck.wayd.model.dto.UserDto;
import blck.wayd.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * User Service.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    @Transactional(readOnly = true)
    public UUID getUserTokenByCredentials(String username, String password) {
        return repository.findByUsernameAndPassword(username, PasswordUtil.hashPassword(password))
                .orElseThrow(() -> new UserNotExists(username))
                .getToken();
    }

    @Transactional
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

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public UUID getUserIdByToken(UUID token) {
        return repository.findByToken(token)
                .orElseThrow(() -> new UserNotExists(token))
                .getId();
    }

    @Transactional(readOnly = true)
    public UserDto getUserByToken(UUID token) {
        return repository.findByToken(token)
                .orElseThrow(() -> new UserNotExists(token))
                .toDto();
    }
}
