package sandbox.interview.charger;

import lombok.val;
import org.junit.Test;
import sandbox.interview.charger.domain.ChargingSession;
import sandbox.interview.charger.domain.StatusEnum;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static sandbox.interview.charger.domain.StatusEnum.FINISHED;
import static sandbox.interview.charger.domain.StatusEnum.IN_PROGRESS;

public class ChargingSessionStorageTest {
    private static final String STATION_ID = "ABC-12345";
    private static final int YEAR = 2019;
    private static final int MONTH = 5;
    private static final int DAY_OF_MONTH = 23;
    private static final int HOUR = 23;
    private static final int MINUTE = 59;
    private static final int SECOND = 59;
    private static final LocalDateTime TIMESTAMP = LocalDateTime.of(YEAR, MONTH, DAY_OF_MONTH, HOUR, MINUTE, SECOND);

    @Test
    public void shouldCreateAndListChargingSessions() {
        val chargingSessionStorage = new ChargingSessionStorage();
        chargingSessionStorage.storeChargingSession(getAChargingSession(TIMESTAMP, IN_PROGRESS));
        chargingSessionStorage.storeChargingSession(getAChargingSession(LocalDateTime.now(), FINISHED));
        val chargingSessions = chargingSessionStorage.listChargingSessions();
        assertThat(chargingSessions, hasSize(2));
    }

    @Test
    public void shouldStopChargingSession() {
        val chargingSessionStorage = new ChargingSessionStorage();
        val chargingSession = getAChargingSession(TIMESTAMP, IN_PROGRESS);
        chargingSessionStorage.storeChargingSession(chargingSession);
        chargingSessionStorage.stopChargingSession(chargingSession.getId());
        val chargingSessions = chargingSessionStorage.listChargingSessions();
        assertThat(chargingSessions, hasSize(1));
        assertThat(chargingSessions.iterator().next().getStatus(), is(FINISHED));
    }

    @Test
    public void shouldGetChargingSessionsSummary() {
        val storage = new ChargingSessionStorage();
        storage.storeChargingSession(getAChargingSession(TIMESTAMP, IN_PROGRESS));
        storage.storeChargingSession(getAChargingSession(TIMESTAMP, FINISHED));
        storage.storeChargingSession(getAChargingSession(LocalDateTime.now(), IN_PROGRESS));
        storage.storeChargingSession(getAChargingSession(LocalDateTime.now(), FINISHED));
        val chargingSessionsSummary = storage.getChargingSessionsSummary();
        assertThat(chargingSessionsSummary.getTotalCount(), is(2L));
        assertThat(chargingSessionsSummary.getStartedCount(), is(1L));
        assertThat(chargingSessionsSummary.getStoppedCount(), is(1L));
    }

    private ChargingSession getAChargingSession(LocalDateTime timestamp, StatusEnum status) {
        return ChargingSession.builder()
                .id(UUID.randomUUID())
                .stationId(STATION_ID)
                .startedAt(timestamp)
                .status(status)
                .build();
    }
}
