package blck.wayd.service;

import blck.wayd.data.dao.UserPreferenceRepository;
import blck.wayd.data.entity.UserPreference;
import blck.wayd.model.dto.UserPreferenceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * User Preference Service.
 */
@Service
@RequiredArgsConstructor
public class UserPreferenceService {

    private final UserPreferenceRepository repository;
    private final UserService userService;

    @Transactional
    public void updateUserPreference(UUID token, UserPreferenceDto preferenceDto) {
        var userId = userService.getUserIdByToken(token);
        var preference = UserPreference.builder()
                .id(userId)
                .appsWhitelist(preferenceDto.getAppsWhitelist())
                .appsBlacklist(preferenceDto.getAppsBlacklist())
                .build();
        repository.save(preference);
    }
}
