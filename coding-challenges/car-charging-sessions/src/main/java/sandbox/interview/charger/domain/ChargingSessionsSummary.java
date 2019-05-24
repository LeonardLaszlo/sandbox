package sandbox.interview.charger.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChargingSessionsSummary {
    private long totalCount;
    private long startedCount;
    private long stoppedCount;
}
