package sandbox.interview.hospital.domain;

import static java.lang.String.format;

public enum PatientState {
    FEVER,
    HEALTHY,
    DIABETES,
    TUBERCULOSIS,
    DEAD;

    public static PatientState from(String healthState) {
        if ("F".equals(healthState)) {
            return FEVER;
        } else if ("H".equals(healthState)) {
            return HEALTHY;
        } else if ("D".equals(healthState)) {
            return DIABETES;
        } else if ("T".equals(healthState)) {
            return TUBERCULOSIS;
        } else if ("X".equals(healthState)) {
            return DEAD;
        } else {
            throw new RuntimeException(format("Invalid health state %s", healthState));
        }
    }
}
