package blck.wayd.service;

import blck.wayd.data.dao.TrackLogRepository;
import blck.wayd.data.entity.TrackLog;
import blck.wayd.exceptions.NoData;
import blck.wayd.model.dto.AppElapsedTimeDto;
import blck.wayd.model.dto.AppUsageBreakdownDto;
import blck.wayd.model.dto.AppUsageBreakdownWrapperDto;
import blck.wayd.model.dto.ConsecutiveAppUsageDto;
import blck.wayd.model.dto.DayAppUsageDto;
import blck.wayd.model.dto.TrackLogDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

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
        var userId = userService.getUserIdByToken(token);
        var logs = logsDtos.stream()
                .map(dto -> TrackLog.fromDto(dto, userId))
                .toList();
        repository.saveAll(logs);
    }

    @Transactional(readOnly = true)
    public List<AppElapsedTimeDto> getAppElapsedTime(UUID token) {
        var user = userService.getUserByToken(token);
        var preference = user.getPreference();
        var elapsedTime = calculateAppsElapsedTime(
                user.getId(), preference.getAppsWhitelist(), preference.getAppsBlacklist());
        if (elapsedTime.isEmpty()) {
            throw new NoData();
        }
        return elapsedTime;
    }

    @Transactional(readOnly = true)
    public AppUsageBreakdownWrapperDto getAppUsageBreakdown(UUID token) {
        var user = userService.getUserByToken(token);
        var preference = user.getPreference();
        var usageBreakdown = calculateAppsUsageBreakdown(user.getId(), preference.getAppsWhitelist(), preference.getAppsBlacklist());
        if (usageBreakdown.appUsageBreakdown().isEmpty()) {
            throw new NoData();
        }
        return usageBreakdown;
    }

    private List<AppElapsedTimeDto> calculateAppsElapsedTime(UUID userId,
                                                             Collection<String> appsWhitelist,
                                                             Collection<String> appsBlacklist) {

        var appUsage = repository.findConsecutiveAppUsageByUserId(userId, appsWhitelist, appsBlacklist);
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

    private AppUsageBreakdownWrapperDto calculateAppsUsageBreakdown(UUID userId,
                                                                    Collection<String> appsWhitelist,
                                                                    Collection<String> appsBlacklist) {

        var appsUsage = repository.findAppUsageBreakdownByUserId(userId, appsWhitelist, appsBlacklist);

        var appsUsageBreakdown = appsUsage.stream()
                .collect(Collectors.groupingBy(DayAppUsageDto::appName))
                .entrySet()
                .stream()
                .map(appUsage -> {
                    var usageBreakdown = appUsage.getValue()
                            .stream()
                            .sorted(Comparator.comparing(DayAppUsageDto::usageDay))
                            .map(DayAppUsageDto::usageSeconds)
                            .map(usage -> isNull(usage) ? BigDecimal.ZERO : usage)
                            .toList();
                    return new AppUsageBreakdownDto(appUsage.getKey(), usageBreakdown);
                })
                .toList();

        var xAxis = appsUsage.stream()
                .map(DayAppUsageDto::usageDay)
                .map(Date::toLocalDate)
                .sorted()
                .distinct()
                .toList();

        return new AppUsageBreakdownWrapperDto(appsUsageBreakdown, xAxis);
    }
}
