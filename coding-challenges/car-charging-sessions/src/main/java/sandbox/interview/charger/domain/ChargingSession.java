package sandbox.interview.charger.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChargingSession {
    private UUID id;
    private String stationId;
    private LocalDateTime startedAt;
    private StatusEnum status;
}

