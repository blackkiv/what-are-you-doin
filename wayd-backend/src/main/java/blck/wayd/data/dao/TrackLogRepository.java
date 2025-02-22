package blck.wayd.data.dao;

import blck.wayd.data.entity.TrackLog;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

/**
 * Track Log Repository.
 */
public interface TrackLogRepository extends ReactiveCrudRepository<TrackLog, UUID> {
}
