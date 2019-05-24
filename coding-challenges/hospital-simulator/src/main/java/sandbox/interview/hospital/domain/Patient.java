package sandbox.interview.hospital.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Patient {
    private PatientState state;
}
