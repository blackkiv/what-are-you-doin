package blck.wayd.data.entity;

import blck.wayd.model.dto.UserPreferenceDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * User Preference.
 */
@Entity(name = "app_user_preference")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPreference {

    @Id
    @Column(name = "user_id")
    private UUID id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "id.userId", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private Set<UserApp> userApps;

    @Column(columnDefinition = "text[]")
    @JdbcTypeCode(SqlTypes.ARRAY)
    private Set<String> appsBlacklist;

    @Column(columnDefinition = "text[]")
    @JdbcTypeCode(SqlTypes.ARRAY)
    private Set<String> appsWhitelist;

    public UserPreferenceDto toDto() {
        var apps = getUserApps().stream()
                .map(UserApp::getId)
                .map(UserApp.Id::getAppName)
                .filter(app -> !appsBlacklist.contains(app) && !appsWhitelist.contains(app))
                .collect(Collectors.toSet());

        return new UserPreferenceDto(apps, getAppsBlacklist(), getAppsWhitelist());
    }
}
