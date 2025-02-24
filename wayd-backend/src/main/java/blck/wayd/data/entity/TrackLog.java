package blck.wayd.data.entity;

import blck.wayd.model.dto.TrackLogDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class TrackLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String appName;
    private Long timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public static TrackLog fromDto(TrackLogDto dto, UUID userId) {
        var log = new TrackLog();
        log.setAppName(dto.appName());
        log.setTimestamp(dto.timestamp());
        var user = new User();
        user.setId(userId);
        log.setUser(user);
        return log;
    }
}
