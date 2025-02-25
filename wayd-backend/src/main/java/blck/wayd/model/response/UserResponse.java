package blck.wayd.model.response;

import blck.wayd.model.dto.UserDto;

import java.util.UUID;

/**
 * User Response.
 */
public record UserResponse(UUID id, String username, UserPreferenceResponse preference) {

    public static UserResponse fromDto(UserDto dto) {
        return new UserResponse(dto.getId(), dto.getUsername(), UserPreferenceResponse.fromDto(dto.getPreference()));
    }
}
