package blck.wayd.model.response;

import blck.wayd.model.dto.AppUsageBreakdownWrapperDto;

import java.time.LocalDate;
import java.util.List;

/**
 * App Usage Breakdown Wrapper Response.
 */
public record AppUsageBreakdownWrapperResponse(List<AppUsageBreakdownResponse> appsUsageBreakdown,
                                               List<LocalDate> xAxis) {

    public static AppUsageBreakdownWrapperResponse fromDto(AppUsageBreakdownWrapperDto dto) {
        var usageBreakdown = dto.appUsageBreakdown().stream()
                .map(AppUsageBreakdownResponse::fromDto)
                .toList();

        return new AppUsageBreakdownWrapperResponse(usageBreakdown, dto.xAxis());
    }
}
