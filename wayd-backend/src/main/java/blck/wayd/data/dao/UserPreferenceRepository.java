package blck.wayd.data.dao;

import blck.wayd.data.entity.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * User Preference Repository.
 */
public interface UserPreferenceRepository extends JpaRepository<UserPreference, UUID> {
}
