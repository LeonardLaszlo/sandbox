package sandbox.interview.charger;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sandbox.interview.charger.domain.ChargingSession;
import sandbox.interview.charger.domain.ChargingSessionsSummary;
import sandbox.interview.charger.domain.CreateChargingSessionRequest;
import sandbox.interview.charger.domain.CreateChargingSessionResponse;
import sandbox.interview.charger.domain.StatusEnum;

import java.util.Collection;
import java.util.UUID;

@RestController
public class ChargingSessionController {
    @Autowired
    private ChargingSessionStorage chargingSessionStorage;

    @PostMapping("/chargingSessions")
    public CreateChargingSessionResponse createChargingSession(@RequestBody CreateChargingSessionRequest request) {
        val chargingSession = ChargingSession.builder()
                .id(UUID.randomUUID())
                .stationId(request.getStationId())
                .startedAt(request.getTimestamp())
                .status(StatusEnum.IN_PROGRESS)
                .build();
        chargingSessionStorage.storeChargingSession(chargingSession);
        return CreateChargingSessionResponse.builder()
                .id(chargingSession.getId())
                .stationId(chargingSession.getStationId())
                .timestamp(chargingSession.getStartedAt())
                .build();
    }

    @PutMapping("/chargingSessions/{id}")
    public ChargingSession stopChargingSession(@PathVariable(name = "id") UUID uuid) {
        return chargingSessionStorage.stopChargingSession(uuid);
    }

    @GetMapping("/chargingSessions")
    public Collection<ChargingSession> listChargingSessions() {
        return chargingSessionStorage.listChargingSessions();
    }

    @GetMapping("/chargingSessions/summary")
    public ChargingSessionsSummary getChargingSessionsSummary() {
        return chargingSessionStorage.getChargingSessionsSummary();
    }
}
