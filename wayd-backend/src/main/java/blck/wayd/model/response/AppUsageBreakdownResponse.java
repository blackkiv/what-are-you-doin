package blck.wayd.model.response;

import blck.wayd.model.dto.AppUsageBreakdownDto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * App Usage Breakdown Response.
 */
public record AppUsageBreakdownResponse(String appName, LocalDate usageDate, BigDecimal usageSeconds) {

    public static AppUsageBreakdownResponse fromDto(AppUsageBreakdownDto dto) {
        return new AppUsageBreakdownResponse(dto.appName(), dto.usageDate(), dto.usageSeconds());
    }
}
