package blck.wayd.service;

import blck.wayd.data.dao.TrackLogRepository;
import blck.wayd.data.entity.TrackLog;
import blck.wayd.model.dto.AppElapsedTimeDto;
import blck.wayd.model.dto.ConsecutiveAppUsageDto;
import blck.wayd.model.dto.TrackLogDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

/**
 * Track Log Service.
 */
@Service
@RequiredArgsConstructor
public class TrackLogService {

    private final UserService userService;
    private final TrackLogRepository repository;

    @Transactional
    public void saveLogs(UUID token, Collection<TrackLogDto> logsDtos) {
        userService.validateUserExistsByTokenAndPropagateUserId(token, (userId) -> {
            var logs = logsDtos.stream()
                    .map(dto -> TrackLog.fromDto(dto, userId))
                    .toList();
            repository.saveAll(logs);
            return emptyList();
        });
    }

    @Transactional(readOnly = true)
    public List<AppElapsedTimeDto> getAppElapsedTime(UUID token) {
        return userService.validateUserExistsByTokenAndPropagateUserId(token, this::calculateAppsElapsedTime);
    }

    private List<AppElapsedTimeDto> calculateAppsElapsedTime(UUID userId) {
        var appUsage = repository.findConsecutiveAppUsageByUserId(userId);
        return appUsage.stream()
                .map(this::calculateAppElapsedTime)
                .collect(Collectors.groupingBy(AppElapsedTimeDto::appName))
                .entrySet()
                .stream()
                .map(appGroup -> {
                    var elapsedTime = appGroup.getValue().stream().map(AppElapsedTimeDto::elapsedTime)
                            .reduce(0L, Long::sum);
                    return new AppElapsedTimeDto(appGroup.getKey(), elapsedTime);
                })
                .toList();
    }

    private AppElapsedTimeDto calculateAppElapsedTime(ConsecutiveAppUsageDto appUsage) {
        return new AppElapsedTimeDto(
                appUsage.appName(),
                appUsage.lastUsageTimestamp() - appUsage.firstUsageTimestamp()
        );
    }
}
