package blck.wayd.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * User Dto.
 */
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDto {

    private UUID id;
    private final String username;
    private String password;
    private UUID token;
    private UserPreferenceDto preference;

    public UserDto(UUID id, String username, UserPreferenceDto preference) {
        this.id = id;
        this.username = username;
        this.preference = preference;
    }
}
