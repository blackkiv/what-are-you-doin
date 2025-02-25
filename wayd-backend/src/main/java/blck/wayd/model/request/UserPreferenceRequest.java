package blck.wayd.model.request;

import java.util.Set;

/**
 * User Preference Request.
 */
public record UserPreferenceRequest(Set<String> appsWhitelist, Set<String> appsBlacklist) {
}
