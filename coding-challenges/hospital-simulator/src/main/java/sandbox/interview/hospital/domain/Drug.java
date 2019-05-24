package sandbox.interview.hospital.domain;

import static java.lang.String.format;

public enum Drug {
    ASPIRIN,
    ANTIBIOTIC,
    INSULIN,
    PARACETAMOL;

    public static Drug from(String drugName) {
        if ("As".equals(drugName)) {
            return ASPIRIN;
        } else if ("An".equals(drugName)) {
            return ANTIBIOTIC;
        } else if ("I".equals(drugName)) {
            return INSULIN;
        } else if ("P".equals(drugName)) {
            return PARACETAMOL;
        } else {
            throw new RuntimeException(format("Invalid drug name: %s", drugName));
        }
    }
}
