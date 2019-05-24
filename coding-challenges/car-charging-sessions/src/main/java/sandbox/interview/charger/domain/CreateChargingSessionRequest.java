package sandbox.interview.charger.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class CreateChargingSessionRequest {
    private String stationId;
    private LocalDateTime timestamp;
}
