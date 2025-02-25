package blck.wayd.model.dto;

import java.time.LocalDate;
import java.util.List;

/**
 * App Usage Breakdown Wrapper Dto.
 */
public record AppUsageBreakdownWrapperDto(List<AppUsageBreakdownDto> appUsageBreakdown, List<LocalDate> xAxis) {
}
