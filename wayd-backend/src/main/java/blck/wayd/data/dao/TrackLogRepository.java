package blck.wayd.data.dao;

import blck.wayd.data.entity.TrackLog;
import blck.wayd.model.dto.ConsecutiveAppUsageDto;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

/**
 * Track Log Repository.
 */
public interface TrackLogRepository extends ReactiveCrudRepository<TrackLog, UUID> {

    /*
        Query generated by chatgpt :D
        TODO: remove this when tracker logic will be updated
     */
    @Query("""
            with ranked_data as (select app_name,
                                        timestamp,
                                        row_number() over (order by timestamp) -
                                        row_number() over (partition by app_name order by timestamp) as group_id
                                 from track_log
                                 where user_id = :userId)
            select app_name       as app_name,
                   min(timestamp) as first_usage_timestamp,
                   max(timestamp) as last_usage_timestamp
            from ranked_data
            group by app_name, group_id
            having min(timestamp) <> max(timestamp)
            order by first_usage_timestamp
            """)
    Flux<ConsecutiveAppUsageDto> findConsecutiveAppUsageByUserId(UUID userId);
}
