package blck.wayd.model.request;

import java.util.Set;

/**
 * Track Logs Request.
 */
public record TrackLogsRequest(Set<TrackLogRequest> logs) {
}
