package blck.wayd.model.request;

import blck.wayd.model.dto.TrackLogDto;

/**
 * Track Log Request.
 */
public record TrackLogRequest(String appName, int timestamp) {

    public TrackLogDto toDto() {
        return new TrackLogDto(appName, timestamp);
    }
}
