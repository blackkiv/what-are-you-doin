package blck.wayd.model.dto;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * Day App Usage Dto.
 */
public record DayAppUsageDto(String appName, Date usageDay, BigDecimal usageSeconds) {
}
