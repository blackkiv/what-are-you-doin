package blck.wayd.resource;

import blck.wayd.model.dto.UserDto;
import blck.wayd.model.dto.UserPreferenceDto;
import blck.wayd.model.request.CreateUserRequest;
import blck.wayd.model.request.UserPreferenceRequest;
import blck.wayd.model.response.UserResponse;
import blck.wayd.service.UserPreferenceService;
import blck.wayd.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * User Resource.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserResource {

    private final UserService userService;
    private final UserPreferenceService userPreferenceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID registerUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(new UserDto(request.username()), request.rawPassword());
    }

    @GetMapping
    public UserResponse getUser(@RequestHeader("User-Token") UUID token) {
        return UserResponse.fromDto(userService.getUserByToken(token));
    }

    @PutMapping("/preference")
    public void updateUserPreference(@RequestHeader("User-Token") UUID token,
                                     @RequestBody UserPreferenceRequest request) {

        var preferenceDto = UserPreferenceDto.builder()
                .appsWhitelist(request.appsWhitelist())
                .appsBlacklist(request.appsBlacklist())
                .build();
        userPreferenceService.updateUserPreference(token, preferenceDto);
    }
}
