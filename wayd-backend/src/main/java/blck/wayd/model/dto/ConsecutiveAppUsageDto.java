package blck.wayd.model.dto;

/**
 * Consecutive App Usage Dto.
 */
public record ConsecutiveAppUsageDto(String appName, long firstUsageTimestamp, long lastUsageTimestamp) {
}
