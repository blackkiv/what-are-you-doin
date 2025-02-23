package blck.wayd.service;

import blck.wayd.data.dao.TrackLogRepository;
import blck.wayd.data.entity.TrackLog;
import blck.wayd.model.dto.AppElapsedTimeDto;
import blck.wayd.model.dto.ConsecutiveAppUsageDto;
import blck.wayd.model.dto.TrackLogDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.UUID;

/**
 * Track Log Service.
 */
@Service
@RequiredArgsConstructor
public class TrackLogService {

    private final UserService userService;
    private final TrackLogRepository repository;

    @Transactional
    public Mono<Void> saveLogs(UUID token, Collection<TrackLogDto> logsDtos) {
        return userService.validateUserExistsByTokenAndPropagateUserId(token, (userId) -> {
            var logs = logsDtos.stream()
                    .map(dto -> TrackLog.fromDto(dto, userId))
                    .toList();
            return repository.saveAll(logs);
        }).then(Mono.empty());
    }

    @Transactional(readOnly = true)
    public Flux<AppElapsedTimeDto> getAppElapsedTime(UUID token) {
        return userService.validateUserExistsByTokenAndPropagateUserId(token,
                (userId) ->
                        repository.findConsecutiveAppUsageByUserId(userId)
                                .map(this::calculateAppElapsedTime)
                                .groupBy(AppElapsedTimeDto::appName)
                                .flatMap(appGroup -> appGroup.map(AppElapsedTimeDto::elapsedTime)
                                        .reduce(0L, Long::sum)
                                        .map(sum -> new AppElapsedTimeDto(appGroup.key(), sum)))
        );
    }

    private AppElapsedTimeDto calculateAppElapsedTime(ConsecutiveAppUsageDto appUsage) {
        return new AppElapsedTimeDto(
                appUsage.appName(),
                appUsage.lastUsageTimestamp() - appUsage.firstUsageTimestamp()
        );
    }
}
