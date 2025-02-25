package blck.wayd.data.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

/**
 * User App.
 */
@Entity(name = "user_apps")
@Getter
@Setter
@NoArgsConstructor
public class UserApp {

    @EmbeddedId
    private Id id;

    @Embeddable
    @Data
    public static class Id implements Serializable {

        private UUID userId;
        private String appName;
    }
}
