package blck.wayd.resource;

import blck.wayd.model.request.AuthorizeRequest;
import blck.wayd.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Auth Resource.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthResource {

    private final UserService userService;

    @PostMapping
    public UUID authorize(@RequestBody AuthorizeRequest request) {
        return userService.getUserTokenByCredentials(request.username(), request.rawPassword());
    }
}
