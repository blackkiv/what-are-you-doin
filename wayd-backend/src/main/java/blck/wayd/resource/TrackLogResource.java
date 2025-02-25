package blck.wayd.resource;

import blck.wayd.model.request.TrackLogRequest;
import blck.wayd.model.request.TrackLogsRequest;
import blck.wayd.model.response.AppElapsedTimeResponse;
import blck.wayd.model.response.AppUsageBreakdownWrapperResponse;
import blck.wayd.service.TrackLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Track Log Resource.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/logs")
public class TrackLogResource {

    private final TrackLogService trackLogService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void syncLogs(@RequestHeader("User-Token") UUID token,
                         @RequestBody TrackLogsRequest request) {

        var logsDtos = request.logs().stream()
                .map(TrackLogRequest::toDto)
                .toList();
        trackLogService.saveLogs(token, logsDtos);
    }

    @GetMapping("/stats")
    public List<AppElapsedTimeResponse> getAppStats(@RequestHeader("User-Token") UUID token) {
        return trackLogService.getAppElapsedTime(token)
                .stream()
                .map(AppElapsedTimeResponse::fromDto)
                .toList();
    }

    @GetMapping("/stats/usage-breakdown")
    public AppUsageBreakdownWrapperResponse getAppUsageBreakdown(@RequestHeader("User-Token") UUID token) {
        return AppUsageBreakdownWrapperResponse.fromDto(trackLogService.getAppUsageBreakdown(token));
    }
}
