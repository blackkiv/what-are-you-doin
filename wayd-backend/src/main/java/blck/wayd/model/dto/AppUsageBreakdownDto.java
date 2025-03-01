package blck.wayd.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * App Usage Breakdown Dto.
 */
public record AppUsageBreakdownDto(String appName, LocalDate usageDate, BigDecimal usageSeconds) {
}
