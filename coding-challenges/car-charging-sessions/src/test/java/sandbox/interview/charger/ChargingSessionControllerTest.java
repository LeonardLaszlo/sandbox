package sandbox.interview.charger;

import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import sandbox.interview.charger.domain.ChargingSession;
import sandbox.interview.charger.domain.ChargingSessionsSummary;
import sandbox.interview.charger.domain.CreateChargingSessionRequest;
import sandbox.interview.charger.domain.CreateChargingSessionResponse;
import sandbox.interview.charger.domain.StatusEnum;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ChargingSessionControllerTest {
    private static final String BASE_URL = "/chargingSessions";
    private static final String STATION_ID = "XYZ-12345";
    private static final int YEAR = 2019;
    private static final int MONTH = 5;
    private static final int DAY_OF_MONTH = 9;
    private static final int HOUR = 13;
    private static final int MINUTE = 0;
    private static final int SECOND = 0;
    private static final LocalDateTime TIMESTAMP = LocalDateTime.of(YEAR, MONTH, DAY_OF_MONTH, HOUR, MINUTE, SECOND);

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ChargingSessionStorage chargingSessionStorage;

    @Test
    public void shouldCreateChargingSession() {
        val response = createChargingSession(TIMESTAMP);
        assertThat(response.getBody(), notNullValue());
        assertThat(response.getBody().getId(), notNullValue());
        assertThat(response.getBody().getStationId(), is(STATION_ID));
        assertThat(response.getBody().getTimestamp(), equalTo(TIMESTAMP));
    }

    @Test
    public void shouldStopChargingSession() {
        val chargingSession = stopChargingSession();
        assertThat(chargingSession.getId(), notNullValue());
        assertThat(chargingSession.getStationId(), is(STATION_ID));
        assertThat(chargingSession.getStartedAt(), equalTo(TIMESTAMP));
        assertThat(chargingSession.getStatus(), equalTo(StatusEnum.FINISHED));
    }

    @Test
    public void shouldListChargingSession() {
        val response = createChargingSession(TIMESTAMP);
        val body = response.getBody();
        assertThat(body, notNullValue());
        val id = body.getId();
        val listResponse = this.restTemplate.getForEntity(BASE_URL, ChargingSession[].class);
        val chargingSessions = listResponse.getBody();
        assertThat(chargingSessions, notNullValue());
        assertThat(chargingSessions.length >= 1, is(true));
        val newChargingSession = Arrays.stream(chargingSessions)
                .filter(chargingSession -> id.equals(chargingSession.getId())).findFirst();
        assertThat(newChargingSession.isPresent(), is(true));
        assertThat(newChargingSession.get().getId(), is(id));
    }

    @Test
    public void shouldGetChargingSessionSummary() {
        createChargingSession(LocalDateTime.now());
        val summary = this.restTemplate.getForEntity(BASE_URL + "/summary", ChargingSessionsSummary.class);
        assertThat(summary.getBody(), notNullValue());
        assertThat(summary.getBody().getTotalCount(), is(1L));
        assertThat(summary.getBody().getStartedCount(), is(1L));
        assertThat(summary.getBody().getStoppedCount(), is(0L));
    }

    private ChargingSession stopChargingSession() {
        val response = createChargingSession(TIMESTAMP);
        val body = response.getBody();
        assertThat(body, notNullValue());
        val id = body.getId();
        this.restTemplate.put(BASE_URL + "/" + id, "");
        val session = chargingSessionStorage.listChargingSessions().stream()
                .filter(chargingSession -> id.equals(chargingSession.getId())).findFirst();
        assertThat(session.isPresent(), is(true));
        return session.get();
    }

    private ResponseEntity<CreateChargingSessionResponse> createChargingSession(LocalDateTime timestamp) {
        val request = getCreateChargingSessionRequest(timestamp);
        return this.restTemplate.postForEntity(BASE_URL, request, CreateChargingSessionResponse.class);
    }

    private CreateChargingSessionRequest getCreateChargingSessionRequest(LocalDateTime timestamp) {
        val request = new CreateChargingSessionRequest();
        request.setStationId(STATION_ID);
        request.setTimestamp(timestamp);
        return request;
    }
}
