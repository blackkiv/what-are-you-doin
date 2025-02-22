package blck.wayd.resource;

import blck.wayd.model.dto.UserDto;
import blck.wayd.model.request.CreateUserRequest;
import blck.wayd.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * User Resource.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserResource {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UUID> registerUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(new UserDto(request.username()), request.rawPassword());
    }
}
