package sandbox.interview.hospital.tools;

import sandbox.interview.hospital.domain.Drug;
import sandbox.interview.hospital.domain.Patient;
import sandbox.interview.hospital.domain.PatientState;

import java.util.List;

import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class ArgumentParser {
    private static final String SEPARATOR = ",";

    private ArgumentParser() {
    }

    public static List<Patient> parsePatients(String patientsAsString) {
        return stream(patientsAsString.split(SEPARATOR))
                .map(PatientState::from)
                .map(Patient::new)
                .collect(toList());
    }

    public static List<Drug> parseDrugs(String drugsAsString) {
        if (isEmpty(drugsAsString)) {
            return emptyList();
        } else {
            return stream(drugsAsString.split(SEPARATOR))
                    .map(Drug::from)
                    .collect(toList());
        }
    }
}
