package blck.wayd.data.entity;

import io.github.joselion.springr2dbcrelationships.annotations.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import java.util.UUID;

/**
 * User.
 */
@Data
@Table(name = "app_user")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class User {

    @Id
    private UUID id;

    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    private UUID token;

    @OneToMany(mappedBy = "user_id")
    private List<TrackLog> trackLogs;
}
