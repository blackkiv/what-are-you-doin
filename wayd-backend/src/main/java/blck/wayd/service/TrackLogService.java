package blck.wayd.service;

import blck.wayd.data.dao.TrackLogRepository;
import blck.wayd.data.entity.TrackLog;
import blck.wayd.model.dto.TrackLogDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.UUID;

/**
 * Track Log Service.
 */
@Service
@RequiredArgsConstructor
public class TrackLogService {

    private final UserService userService;
    private final TrackLogRepository repository;

    @Transactional
    public Mono<Void> saveLogs(UUID userId, Collection<TrackLogDto> logsDtos) {
        var logs = logsDtos.stream()
                .map(dto -> TrackLog.fromDto(dto, userId))
                .toList();
        Mono<Void> saveProducer = repository.saveAll(logs)
                .then(Mono.empty());
        return userService.validateUserExistsByToken(userId, saveProducer);
    }
}
