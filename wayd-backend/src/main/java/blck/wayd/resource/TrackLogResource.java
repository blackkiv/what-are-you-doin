package blck.wayd.resource;

import blck.wayd.model.request.TrackLogRequest;
import blck.wayd.model.request.TrackLogsRequest;
import blck.wayd.service.TrackLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

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
    public Mono<Void> syncLogs(@RequestHeader("User-Id") UUID userId,
                               @RequestBody TrackLogsRequest request) {

        var logsDtos = request.logs().stream()
                .map(TrackLogRequest::toDto)
                .toList();
        return trackLogService.saveLogs(userId, logsDtos);
    }
}
