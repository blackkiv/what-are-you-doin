package blck.wayd.model.response;

import blck.wayd.model.dto.AppUsageBreakdownDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * App Usage Breakdown Response.
 */
public record AppUsageBreakdownResponse(String appName, List<BigDecimal> usageBreakdown) {

    public static AppUsageBreakdownResponse fromDto(AppUsageBreakdownDto dto) {
        return new AppUsageBreakdownResponse(dto.appName(), dto.usageBreakdown());
    }
}
