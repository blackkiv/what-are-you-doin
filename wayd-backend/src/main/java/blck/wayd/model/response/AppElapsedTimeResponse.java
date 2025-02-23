package blck.wayd.model.response;

import blck.wayd.model.dto.AppElapsedTimeDto;

/**
 * App Elapsed Time Response.
 */
public record AppElapsedTimeResponse(String appName, long elapsedTime) {

    public static AppElapsedTimeResponse fromDto(AppElapsedTimeDto dto) {
        return new AppElapsedTimeResponse(dto.appName(), dto.elapsedTime());
    }
}
