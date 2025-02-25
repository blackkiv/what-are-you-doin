package blck.wayd.model.response;

import blck.wayd.model.dto.UserPreferenceDto;

import java.util.Set;

/**
 * User Preference Response.
 */
public record UserPreferenceResponse(Set<String> apps, Set<String> appsBlacklist, Set<String> appsWhitelist) {

    public static UserPreferenceResponse fromDto(UserPreferenceDto dto) {
        return new UserPreferenceResponse(dto.getApps(), dto.getAppsBlacklist(), dto.getAppsWhitelist());
    }
}
