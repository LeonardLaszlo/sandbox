package sandbox.interview.charger;

import lombok.val;
import org.springframework.stereotype.Service;
import sandbox.interview.charger.domain.ChargingSession;
import sandbox.interview.charger.domain.ChargingSessionsSummary;
import sandbox.interview.charger.exceptions.ChargingSessionNotFoundException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

import static sandbox.interview.charger.domain.StatusEnum.FINISHED;
import static sandbox.interview.charger.domain.StatusEnum.IN_PROGRESS;

@Service
public class ChargingSessionStorage {
    private Map<UUID, ChargingSession> sessions = new HashMap<>();

    synchronized void storeChargingSession(ChargingSession chargingSession) {
        sessions.putIfAbsent(chargingSession.getId(), chargingSession);
    }

    synchronized ChargingSession stopChargingSession(UUID uuid) {
        val chargingSession = sessions.get(uuid);
        if (chargingSession == null) {
            throw new ChargingSessionNotFoundException();
        } else {
            val id = chargingSession.getId();
            val updatedChargingSession = ChargingSession.builder()
                    .id(id)
                    .stationId(chargingSession.getStationId())
                    .startedAt(chargingSession.getStartedAt())
                    .status(FINISHED)
                    .build();
            sessions.put(id, updatedChargingSession);
            return updatedChargingSession;
        }
    }

    synchronized Collection<ChargingSession> listChargingSessions() {
        return sessions.values();
    }

    /**
     * This method cannot be implemented with an algorithmic complexity less than O(n) as specified in the requirement
     * because to compute the total number of charger sessions for the last minute at least one iteration over the
     * stored sessions is needed.
     * However if the statistics would be computed over the whole time it is possible to compute the statistics with
     * O(1) complexity by updating the stats when a create/update operation is executed.
     */
    synchronized ChargingSessionsSummary getChargingSessionsSummary() {
        Predicate<ChargingSession> isInLastMinute = chargingSession -> {
            val secondsInBetween = ChronoUnit.SECONDS.between(chargingSession.getStartedAt(), LocalDateTime.now());
            return secondsInBetween >= 0 && secondsInBetween <= 60;
        };
        Predicate<ChargingSession> isInProgress = chargingSession -> IN_PROGRESS.equals(chargingSession.getStatus());
        Predicate<ChargingSession> isFinished = chargingSession -> FINISHED.equals(chargingSession.getStatus());
        val sessions = this.sessions.values();
        return ChargingSessionsSummary.builder()
                .totalCount(sessions.stream().filter(isInLastMinute).count())
                .startedCount(sessions.stream().filter(isInLastMinute).filter(isInProgress).count())
                .stoppedCount(sessions.stream().filter(isInLastMinute).filter(isFinished).count())
                .build();
    }
}
