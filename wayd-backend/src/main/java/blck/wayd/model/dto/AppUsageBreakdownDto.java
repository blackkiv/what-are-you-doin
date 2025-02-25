package blck.wayd.model.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * App Usage Breakdown Dto.
 */
public record AppUsageBreakdownDto(String appName, List<BigDecimal> usageBreakdown) {
}
