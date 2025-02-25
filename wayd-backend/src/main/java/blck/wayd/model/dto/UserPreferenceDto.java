package blck.wayd.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * User Preference Dto.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserPreferenceDto {

    private Set<String> apps;
    private Set<String> appsBlacklist;
    private Set<String> appsWhitelist;
}
