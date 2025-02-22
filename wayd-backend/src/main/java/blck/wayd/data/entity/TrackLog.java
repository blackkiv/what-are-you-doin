package blck.wayd.data.entity;

import blck.wayd.model.dto.TrackLogDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.UUID;

/**
 * Track Log.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class TrackLog {

    @Id
    private UUID id;

    @NonNull
    private String appName;

    @NonNull
    private Integer timestamp;

    @NonNull
    private UUID userId;

    public static TrackLog fromDto(TrackLogDto dto, UUID userId) {
        return new TrackLog(dto.appName(), dto.timestamp(), userId);
    }
}
